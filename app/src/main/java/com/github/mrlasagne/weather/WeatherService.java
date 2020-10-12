package com.github.mrlasagne.weather;

import java.io.IOException;

public interface WeatherService {

    Weather getWeatherAt(Address address) throws IOException;
}
