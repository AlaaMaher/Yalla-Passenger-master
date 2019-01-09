package com.asi.yalla_passanger_eg.Models;

/**
 * Created by m.khalid on 7/3/2017.
 */

public class LatLngModel
{
    Double lat,lng;

    public LatLngModel(Double lat, Double lng)
    {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat()
    {
        return lat;
    }

    public Double getLng()
    {
        return lng;
    }
}
