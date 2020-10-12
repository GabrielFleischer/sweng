package com.github.mrlasagne.weather;

public final class Weather {

    private final int id;
    private final String main;
    private final String desc;
    private final String icon;

    public Weather(int id, String main, String desc, String icon) {
        this.id = id;
        this.main = main;
        this.desc = desc;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }
}
