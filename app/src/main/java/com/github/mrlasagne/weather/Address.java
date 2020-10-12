package com.github.mrlasagne.weather;

public final class Address {

    private final String locality, area, country;

    public Address(String locality, String area, String country) {
        this.locality = locality;
        this.area = area;
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public String getArea() {
        return area;
    }

    public String getCountry() {
        return country;
    }
}
