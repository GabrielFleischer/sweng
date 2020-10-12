package com.github.mrlasagne.weather;

import android.content.Intent;
import android.location.Location;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.github.mrlasagne.weather.activities.MainActivity;
import com.github.mrlasagne.weather.activities.WeatherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@UninstallModules(WeatherModule.class)
@HiltAndroidTest
public final class WeatherActivityTest {

    private static final Location CUR_LOCATION = new Location((String) null);
    private static final Address  CUR_ADDRESS  = new Address("1", "1", "1");
    private static final Weather  CUR_WEATHER  = new Weather(0, "Cloudy", "Some clouds", "NULL");
    private static final String ADDRESS = "Test";

    @Rule public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @BindValue public LocationService locationService = Mockito.mock(LocationService.class);
    @BindValue public GeocodeService geocodeService = Mockito.mock(GeocodeService.class);
    @BindValue public WeatherService weatherService = Mockito.mock(WeatherService.class);

    @Test
    public void getCurrentWeatherWorks() throws IOException {
        Mockito.when(locationService.getDeviceLocation()).thenReturn(CUR_LOCATION);
        Mockito.when(geocodeService.convertToAddress(CUR_LOCATION)).thenReturn(CUR_ADDRESS);
        Mockito.when(weatherService.getWeatherAt(CUR_ADDRESS)).thenReturn(CUR_WEATHER);

        final Intent intent = new Intent(ApplicationProvider.getApplicationContext(), WeatherActivity.class);
        intent.putExtra(MainActivity.EXTRA_WEATHER, (String) null);
        try (ActivityScenario<WeatherActivity> scenario = ActivityScenario.launch(intent)) {
            onView(withId(R.id.weatherMain))
                    .check(matches(withText(CUR_WEATHER.getMain())));
            onView(withId(R.id.desc))
                    .check(matches(withText(CUR_WEATHER.getDescription())));
        }

        Mockito.verify(locationService).getDeviceLocation();
        Mockito.verify(geocodeService).convertToAddress(CUR_LOCATION);
        Mockito.verify(weatherService).getWeatherAt(CUR_ADDRESS);
    }

    @Test
    public void getWeatherAtWorks() throws IOException {
        Mockito.when(geocodeService.getAddress(ADDRESS)).thenReturn(CUR_ADDRESS);
        Mockito.when(weatherService.getWeatherAt(CUR_ADDRESS)).thenReturn(CUR_WEATHER);

        final Intent intent = new Intent(ApplicationProvider.getApplicationContext(), WeatherActivity.class);
        intent.putExtra(MainActivity.EXTRA_WEATHER, ADDRESS);
        try (ActivityScenario<WeatherActivity> scenario = ActivityScenario.launch(intent)) {
            onView(withId(R.id.weatherMain))
                    .check(matches(withText(CUR_WEATHER.getMain())));
            onView(withId(R.id.desc))
                    .check(matches(withText(CUR_WEATHER.getDescription())));
        }

        Mockito.verify(geocodeService).getAddress(ADDRESS);
        Mockito.verify(weatherService).getWeatherAt(CUR_ADDRESS);
    }
}
