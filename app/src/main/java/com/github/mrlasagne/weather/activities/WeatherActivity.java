package com.github.mrlasagne.weather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mrlasagne.weather.Address;
import com.github.mrlasagne.weather.GeocodeService;
import com.github.mrlasagne.weather.LocationService;
import com.github.mrlasagne.weather.R;
import com.github.mrlasagne.weather.Weather;
import com.github.mrlasagne.weather.WeatherService;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {

    @Inject LocationService locationService;
    @Inject GeocodeService geocodeService;
    @Inject WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        TextView main = findViewById(R.id.weatherMain);
        TextView desc = findViewById(R.id.desc);

        final String loc = intent.getStringExtra(MainActivity.EXTRA_WEATHER);
        try {
            final Address address = loc == null ? geocodeService.convertToAddress(locationService.getDeviceLocation()) : geocodeService.getAddress(loc);
            if(address == null) return;
            final Weather weather = weatherService.getWeatherAt(address);

            main.setText(weather.getMain());
            desc.setText(weather.getDescription());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}