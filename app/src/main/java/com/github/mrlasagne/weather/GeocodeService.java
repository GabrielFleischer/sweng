package com.github.mrlasagne.weather;

import android.location.Location;

import java.io.IOException;

public interface GeocodeService {

    Address convertToAddress(Location location) throws IOException;

    Address getAddress(String address) throws IOException;
}
