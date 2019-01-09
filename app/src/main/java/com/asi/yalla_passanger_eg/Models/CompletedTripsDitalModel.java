
package com.asi.yalla_passanger_eg.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletedTripsDitalModel {

    @SerializedName("TripId")
    @Expose
    private String tripId;
    @SerializedName("PickUpPlace")
    @Expose
    private String pickUpPlace;
    @SerializedName("DropPlace")
    @Expose
    private String dropPlace;
    @SerializedName("PickUpTime")
    @Expose
    private String pickUpTime;
    @SerializedName("DriverId")
    @Expose
    private String driverId;
    @SerializedName("TaxiId")
    @Expose
    private String taxiId;
    @SerializedName("CompanyId")
    @Expose
    private String companyId;
    @SerializedName("PassengerId")
    @Expose
    private String passengerId;
    @SerializedName("RoundTrip")
    @Expose
    private String roundTrip;
    @SerializedName("DriverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("SubLogId")
    @Expose
    private String subLogId;
    @SerializedName("DropLatitude")
    @Expose
    private String dropLatitude;
    @SerializedName("DropLongitude")
    @Expose
    private String dropLongitude;
    @SerializedName("PickupLatitude")
    @Expose
    private String pickupLatitude;
    @SerializedName("PickupLongitude")
    @Expose
    private String pickupLongitude;
    @SerializedName("DriverName")
    @Expose
    private String driverName;
    @SerializedName("BookedBy")
    @Expose
    private String bookedBy;
    @SerializedName("DriverProfileImage")
    @Expose
    private String driverProfileImage;
    @SerializedName("EstimatedTime")
    @Expose
    private String estimatedTime;
    @SerializedName("NotificationTime")
    @Expose
    private String notificationTime;
    @SerializedName("NotificationMinutes")
    @Expose
    private String notificationMinutes;
    @SerializedName("NotificationSeconds")
    @Expose
    private String notificationSeconds;
    @SerializedName("DriverNotes")
    @Expose
    private String driverNotes;
    @SerializedName("TaxiMinimumSpeed")
    @Expose
    private String taxiMinimumSpeed;
    @SerializedName("WaitingTime")
    @Expose
    private String waitingTime;
    @SerializedName("Distance")
    @Expose
    private String distance;
    @SerializedName("Fare")
    @Expose
    private String fare;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("TimeToReachPassenger")
    @Expose
    private String timeToReachPassenger;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("Flag")
    @Expose
    private String flag;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getPickUpPlace() {
        return pickUpPlace;
    }

    public void setPickUpPlace(String pickUpPlace) {
        this.pickUpPlace = pickUpPlace;
    }

    public String getDropPlace() {
        return dropPlace;
    }

    public void setDropPlace(String dropPlace) {
        this.dropPlace = dropPlace;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(String roundTrip) {
        this.roundTrip = roundTrip;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSubLogId() {
        return subLogId;
    }

    public void setSubLogId(String subLogId) {
        this.subLogId = subLogId;
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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getDriverProfileImage() {
        return driverProfileImage;
    }

    public void setDriverProfileImage(String driverProfileImage) {
        this.driverProfileImage = driverProfileImage;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationMinutes() {
        return notificationMinutes;
    }

    public void setNotificationMinutes(String notificationMinutes) {
        this.notificationMinutes = notificationMinutes;
    }

    public String getNotificationSeconds() {
        return notificationSeconds;
    }

    public void setNotificationSeconds(String notificationSeconds) {
        this.notificationSeconds = notificationSeconds;
    }

    public String getDriverNotes() {
        return driverNotes;
    }

    public void setDriverNotes(String driverNotes) {
        this.driverNotes = driverNotes;
    }

    public String getTaxiMinimumSpeed() {
        return taxiMinimumSpeed;
    }

    public void setTaxiMinimumSpeed(String taxiMinimumSpeed) {
        this.taxiMinimumSpeed = taxiMinimumSpeed;
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

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimeToReachPassenger() {
        return timeToReachPassenger;
    }

    public void setTimeToReachPassenger(String timeToReachPassenger) {
        this.timeToReachPassenger = timeToReachPassenger;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
