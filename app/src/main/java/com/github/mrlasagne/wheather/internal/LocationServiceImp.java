package com.github.mrlasagne.wheather.internal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.github.mrlasagne.wheather.LocationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LocationServiceImp implements LocationService {

    private static final List<String> PERMISSIONS = Collections.unmodifiableList(Arrays.asList(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION));

    private final Activity mainActivity;
    private final LocationManager locationManager;

    public LocationServiceImp(final Activity activity) {
        this.mainActivity = activity;
        this.locationManager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public Location getDeviceLocation() {
        if (PERMISSIONS.stream().anyMatch(perm -> ActivityCompat.checkSelfPermission(mainActivity.getApplicationContext(), perm) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(mainActivity,
                    PERMISSIONS.toArray(new String[0]),
                    PackageManager.PERMISSION_GRANTED);

            return null;
        } else {
            final List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (final String provider : providers) {
                final Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }

            return bestLocation;
        }
    }
}
