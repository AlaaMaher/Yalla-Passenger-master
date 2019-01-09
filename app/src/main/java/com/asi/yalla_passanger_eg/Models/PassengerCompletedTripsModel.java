
package com.asi.yalla_passanger_eg.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengerCompletedTripsModel {

    @SerializedName("TripId")
    @Expose
    private String tripId;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("TripDistance")
    @Expose
    private String tripDistance;
    @SerializedName("TripFare")
    @Expose
    private String tripFare;
    @SerializedName("TripRate")
    @Expose
    private String tripRate;
    @SerializedName("DriverImage")
    @Expose
    private String driverImage;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(String tripDistance) {
        this.tripDistance = tripDistance;
    }

    public String getTripFare() {
        return tripFare;
    }

    public void setTripFare(String tripFare) {
        this.tripFare = tripFare;
    }

    public String getTripRate() {
        return tripRate;
    }

    public void setTripRate(String tripRate) {
        this.tripRate = tripRate;
    }

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

}
