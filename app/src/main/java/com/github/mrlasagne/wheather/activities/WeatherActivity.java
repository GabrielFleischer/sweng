package com.github.mrlasagne.wheather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mrlasagne.wheather.GeocodingService;
import com.github.mrlasagne.wheather.LocationService;
import com.github.mrlasagne.wheather.R;
import com.github.mrlasagne.wheather.Weather;
import com.github.mrlasagne.wheather.WeatherService;
import com.github.mrlasagne.wheather.internal.GeocodingServiceImp;
import com.github.mrlasagne.wheather.internal.LocationServiceImp;
import com.github.mrlasagne.wheather.internal.WeatherServiceImp;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        TextView main = findViewById(R.id.weatherMain);
        TextView desc = findViewById(R.id.desc);
        
        final LocationService locationService = new LocationServiceImp(this);
        final GeocodingService geocodingService = new GeocodingServiceImp(this.getApplicationContext());
        final WeatherService weatherService = new WeatherServiceImp(this, getString(R.string.openweather_api_key));

        final String loc = intent.getStringExtra(MainActivity.EXTRA_WEATHER);
        try {
            final Address address = loc == null ? geocodingService.convertToAddress(locationService.getDeviceLocation()) : geocodingService.getAddress(loc);
            if(address == null) return;
            final Weather weather = weatherService.getWeatherAt(address);

            main.setText(weather.getMain());
            desc.setText(weather.getDescription());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}