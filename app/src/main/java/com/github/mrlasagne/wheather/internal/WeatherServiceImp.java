package com.github.mrlasagne.wheather.internal;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.StrictMode;
import android.util.JsonReader;

import androidx.core.app.ActivityCompat;

import com.github.mrlasagne.wheather.Weather;
import com.github.mrlasagne.wheather.WeatherService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherServiceImp implements WeatherService {

    private final Activity mainActivity;
    private final String key;

    public WeatherServiceImp(Activity mainActivity, String key) {
        this.mainActivity = mainActivity;
        this.key = key;
    }

    @Override
    public Weather getWeatherAt(Address address) throws IOException {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{ Manifest.permission.INTERNET },
                    PackageManager.PERMISSION_GRANTED);

            return null;
        } else {
            // TODO: Remove this line of code once I learn about asynchronous operations!
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

            URL url = new URL(String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s,%s&appid=%s",
                    address.getLocality(),
                    address.getAdminArea(),
                    address.getCountryCode(),
                    key));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200)
                throw new IOException("Bad response !");

            InputStream stream = connection.getInputStream();

            final JsonReader reader = new JsonReader(new InputStreamReader(stream));
            reader.beginObject();
            reader.nextName();

            // coord
            reader.beginObject();
                reader.nextName(); reader.nextDouble();
                reader.nextName(); reader.nextDouble();
            reader.endObject();
            //weather
            reader.nextName();
                reader.beginArray();
                    reader.beginObject();
                        reader.nextName(); final int id = reader.nextInt();
                        reader.nextName(); final String main = reader.nextString();
                        reader.nextName(); final String desc = reader.nextString();
                        reader.nextName(); final String icon = reader.nextString();
            reader.close();
            stream.close();
            connection.disconnect();

            return new Weather(id, main, desc, icon);
        }
    }
}
