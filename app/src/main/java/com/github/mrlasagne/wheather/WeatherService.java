package com.github.mrlasagne.wheather;

import android.location.Address;

import java.io.IOException;

public interface WeatherService {

    Weather getWeatherAt(Address address) throws IOException;
}
