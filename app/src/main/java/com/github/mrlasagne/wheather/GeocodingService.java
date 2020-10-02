package com.github.mrlasagne.wheather;

import android.location.Address;
import android.location.Location;

import java.io.IOException;

public interface GeocodingService {

    Address convertToAddress(Location location) throws IOException;

    Address getAddress(String address) throws IOException;
}
