package com.github.mrlasagne.wheather;

import com.github.mrlasagne.wheather.internal.GeocodingServiceImp;
import com.github.mrlasagne.wheather.internal.LocationServiceImp;
import com.github.mrlasagne.wheather.internal.WeatherServiceImp;

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
    public abstract GeocodingService bindGeocodingService(
            GeocodingServiceImp geocodingServiceImp
    );

    @Binds
    public abstract WeatherService bindWeatherService(
            WeatherServiceImp weatherServiceImp
    );
}