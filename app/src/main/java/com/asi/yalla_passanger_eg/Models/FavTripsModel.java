
package com.asi.yalla_passanger_eg.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavTripsModel {

    @SerializedName("PassengerId")
    @Expose
    private String passengerId;
    @SerializedName("FavouriteId")
    @Expose
    private String favouriteId;
    @SerializedName("PickupPlace")
    @Expose
    private String pickupPlace;
    @SerializedName("DropPlace")
    @Expose
    private String dropPlace;
    @SerializedName("PickupLatitude")
    @Expose
    private String pickupLatitude;
    @SerializedName("PickLongitude")
    @Expose
    private String pickLongitude;
    @SerializedName("DropLatitude")
    @Expose
    private String dropLatitude;
    @SerializedName("DropLongitude")
    @Expose
    private String dropLongitude;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("LocationType")
    @Expose
    private String locationType;
    @SerializedName("Notes")
    @Expose
    private String notes;

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(String favouriteId) {
        this.favouriteId = favouriteId;
    }

    public String getPickupPlace() {
        return pickupPlace;
    }

    public void setPickupPlace(String pickupPlace) {
        this.pickupPlace = pickupPlace;
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

    public String getPickLongitude() {
        return pickLongitude;
    }

    public void setPickLongitude(String pickLongitude) {
        this.pickLongitude = pickLongitude;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
