package com.lyonsys.cyclehire;

/**
 * Created by yong on 14/06/2016.
 */
public class Location {

    private double latitude;
    private double longitude;


    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static double getDistanceBetween(Location location1, Location location2)
    {
//todo: use real world calcuaotr
        return Math.abs(location1.getLatitude() - location2.getLatitude())
                 + Math.abs(location1.getLongitude() - location2.getLongitude());
    }
}
