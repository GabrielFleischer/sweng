package com.github.mrlasagne.wheather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.mrlasagne.wheather.R;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_WEATHER = MainActivity.class + ":WEATHER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openWeather(View view) {
        Intent intent = new Intent(this, WeatherActivity.class);

        EditText field = findViewById(R.id.cust_address);

        intent.putExtra(EXTRA_WEATHER, view.getId() == R.id.weather_cur_loc ? null : field.getText().toString());
        startActivity(intent);
    }
}