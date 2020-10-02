package com.github.mrlasagne.wheather.internal;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.github.mrlasagne.wheather.GeocodingService;

import java.io.IOException;
import java.util.List;

public class GeocodingServiceImp implements GeocodingService {

    private final Geocoder geocoder;

    public GeocodingServiceImp(final Context context) {
        geocoder = new Geocoder(context);
    }

    @Override
    public Address convertToAddress(Location location) throws IOException {
        if(location == null)
            return null;
        final List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        if(addresses.size() > 0)
            return addresses.get(0);
        return null;
    }

    @Override
    public Address getAddress(String address) throws IOException {
        final List<Address> addresses = geocoder.getFromLocationName(address, 1);
        if(addresses.size() > 0)
            return addresses.get(0);
        return null;
    }
}