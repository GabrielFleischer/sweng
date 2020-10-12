package com.github.mrlasagne.weather.internal;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;

import com.github.mrlasagne.weather.Address;
import com.github.mrlasagne.weather.GeocodeService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class GeocodeServiceImp implements GeocodeService {

    private final Geocoder geocoder;

    @Inject
    public GeocodeServiceImp(@ApplicationContext final Context context) {
        geocoder = new Geocoder(context);
    }

    @Override
    public Address convertToAddress(Location location) throws IOException {
        if(location == null)
            return null;
        final List<android.location.Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        if(addresses.size() > 0) {
            final android.location.Address address = addresses.get(0);
            return new Address(address.getLocality(), address.getAdminArea(), address.getCountryCode());
        }
        return null;
    }

    @Override
    public Address getAddress(String address) throws IOException {
        final List<android.location.Address> addresses = geocoder.getFromLocationName(address, 1);
        if(addresses.size() > 0) {
            final android.location.Address a = addresses.get(0);
            return new Address(a.getLocality(), a.getAdminArea(), a.getCountryCode());
        }
        return null;
    }
}
