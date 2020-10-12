package com.github.mrlasagne.weather;

import com.github.mrlasagne.weather.internal.GeocodeServiceImp;
import com.github.mrlasagne.weather.internal.LocationServiceImp;
import com.github.mrlasagne.weather.internal.WeatherServiceImp;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class WeatherModule {

    @Binds
    public abstract LocationService bindLocationService(
            LocationServiceImp locationServiceImp
    );

    @Binds
    public abstract GeocodeService bindGeocodingService(
            GeocodeServiceImp geocodingServiceImp
    );

    @Binds
    public abstract WeatherService bindWeatherService(
            WeatherServiceImp weatherServiceImp
    );
}
