
package com.asi.yalla_passanger_eg.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewTripDataModel {

    @SerializedName("TripId")
    @Expose
    private String tripId;
    @SerializedName("PickUpPlace")
    @Expose
    private String pickUpPlace;
    @SerializedName("DropPlace")
    @Expose
    private String dropPlace;
    @SerializedName("PickupLatitude")
    @Expose
    private String pickupLatitude;
    @SerializedName("DropLatitude")
    @Expose
    private String dropLatitude;
    @SerializedName("PickupLongitude")
    @Expose
    private String pickupLongitude;
    @SerializedName("DropLongitude")
    @Expose
    private String dropLongitude;
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
    @SerializedName("DriverId")
    @Expose
    private String driverId;
    @SerializedName("DriverName")
    @Expose
    private String driverName;
    @SerializedName("DriverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("DriverProfileImage")
    @Expose
    private String driverProfileImage;
    @SerializedName("TimeToReachPassenger")
    @Expose
    private String timeToReachPassenger;
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

    public String getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(String pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public String getDropLatitude() {
        return dropLatitude;
    }

    public void setDropLatitude(String dropLatitude) {
        this.dropLatitude = dropLatitude;
    }

    public String getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(String pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public String getDropLongitude() {
        return dropLongitude;
    }

    public void setDropLongitude(String dropLongitude) {
        this.dropLongitude = dropLongitude;
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverProfileImage() {
        return driverProfileImage;
    }

    public void setDriverProfileImage(String driverProfileImage) {
        this.driverProfileImage = driverProfileImage;
    }

    public String getTimeToReachPassenger() {
        return timeToReachPassenger;
    }

    public void setTimeToReachPassenger(String timeToReachPassenger) {
        this.timeToReachPassenger = timeToReachPassenger;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
