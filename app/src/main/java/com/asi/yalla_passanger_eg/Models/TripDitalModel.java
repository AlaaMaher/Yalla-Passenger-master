
package com.asi.yalla_passanger_eg.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripDitalModel {

    @SerializedName("TripId")
    @Expose
    private String tripId;
    @SerializedName("CurrentLocation")
    @Expose
    private String currentLocation;
    @SerializedName("DropLocation")
    @Expose
    private String dropLocation;
    @SerializedName("PickupTime")
    @Expose
    private String pickupTime;
    @SerializedName("ActualPickupTime")
    @Expose
    private String actualPickupTime;
    @SerializedName("DriverTotalRating")
    @Expose
    private String driverTotalRating;
    @SerializedName("DriverNotes")
    @Expose
    private String driverNotes;
    @SerializedName("TravelStatus")
    @Expose
    private String travelStatus;
    @SerializedName("DriverReply")
    @Expose
    private String driverReply;
    @SerializedName("PickupLatitude")
    @Expose
    private String pickupLatitude;
    @SerializedName("PickupLongitude")
    @Expose
    private String pickupLongitude;
    @SerializedName("DropLatitude")
    @Expose
    private String dropLatitude;
    @SerializedName("DropLongitude")
    @Expose
    private String dropLongitude;
    @SerializedName("TimeToReachPassenger")
    @Expose
    private String timeToReachPassenger;
    @SerializedName("WaitingTime")
    @Expose
    private String waitingTime;
    @SerializedName("Distance")
    @Expose
    private String distance;
    @SerializedName("TaxiId")
    @Expose
    private String taxiId;
    @SerializedName("TaxiNumber")
    @Expose
    private String taxiNumber;
    @SerializedName("TaxiManufacturer")
    @Expose
    private String taxiManufacturer;
    @SerializedName("TaxiMinSpeed")
    @Expose
    private String taxiMinSpeed;
    @SerializedName("DriverId")
    @Expose
    private String driverId;
    @SerializedName("DriverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("DriverName")
    @Expose
    private String driverName;
    @SerializedName("DriverImage")
    @Expose
    private String driverImage;
    @SerializedName("DriverStatus")
    @Expose
    private String driverStatus;
    @SerializedName("DriverLatitude")
    @Expose
    private String driverLatitude;
    @SerializedName("DriverLongitude")
    @Expose
    private String driverLongitude;
    @SerializedName("Flag")
    @Expose
    private String flag;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getActualPickupTime() {
        return actualPickupTime;
    }

    public void setActualPickupTime(String actualPickupTime) {
        this.actualPickupTime = actualPickupTime;
    }

    public String getDriverTotalRating() {
        return driverTotalRating;
    }

    public void setDriverTotalRating(String driverTotalRating) {
        this.driverTotalRating = driverTotalRating;
    }

    public String getDriverNotes() {
        return driverNotes;
    }

    public void setDriverNotes(String driverNotes) {
        this.driverNotes = driverNotes;
    }

    public String getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(String travelStatus) {
        this.travelStatus = travelStatus;
    }

    public String getDriverReply() {
        return driverReply;
    }

    public void setDriverReply(String driverReply) {
        this.driverReply = driverReply;
    }

    public String getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(String pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public String getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(String pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public String getDropLatitude() {
        return dropLatitude;
    }

    public void setDropLatitude(String dropLatitude) {
        this.dropLatitude = dropLatitude;
    }

    public String getDropLongitude() {
        return dropLongitude;
    }

    public void setDropLongitude(String dropLongitude) {
        this.dropLongitude = dropLongitude;
    }

    public String getTimeToReachPassenger() {
        return timeToReachPassenger;
    }

    public void setTimeToReachPassenger(String timeToReachPassenger) {
        this.timeToReachPassenger = timeToReachPassenger;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getTaxiNumber() {
        return taxiNumber;
    }

    public void setTaxiNumber(String taxiNumber) {
        this.taxiNumber = taxiNumber;
    }

    public String getTaxiManufacturer() {
        return taxiManufacturer;
    }

    public void setTaxiManufacturer(String taxiManufacturer) {
        this.taxiManufacturer = taxiManufacturer;
    }

    public String getTaxiMinSpeed() {
        return taxiMinSpeed;
    }

    public void setTaxiMinSpeed(String taxiMinSpeed) {
        this.taxiMinSpeed = taxiMinSpeed;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverLatitude() {
        return driverLatitude;
    }

    public void setDriverLatitude(String driverLatitude) {
        this.driverLatitude = driverLatitude;
    }

    public String getDriverLongitude() {
        return driverLongitude;
    }

    public void setDriverLongitude(String driverLongitude) {
        this.driverLongitude = driverLongitude;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
