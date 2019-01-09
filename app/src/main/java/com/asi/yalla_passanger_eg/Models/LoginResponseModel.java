package com.asi.yalla_passanger_eg.Models;

/**
 * Created by m.khalid on 7/3/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("ProfileImage")
    @Expose
    private String profileImage;
    @SerializedName("LoginFrom")
    @Expose
    private String loginFrom;
    @SerializedName("RefferalCode")
    @Expose
    private String refferalCode;
    @SerializedName("RefferalCodeAmount")
    @Expose
    private String refferalCodeAmount;
    @SerializedName("WalletAmount")
    @Expose
    private String walletAmount;
    @SerializedName("TellToFriendMessage")
    @Expose
    private String tellToFriendMessage;
    @SerializedName("TellToFriendMessageSubject")
    @Expose
    private String tellToFriendMessageSubject;
    @SerializedName("Flag")
    @Expose
    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getRefferalCode() {
        return refferalCode;
    }

    public void setRefferalCode(String refferalCode) {
        this.refferalCode = refferalCode;
    }

    public String getRefferalCodeAmount() {
        return refferalCodeAmount;
    }

    public void setRefferalCodeAmount(String refferalCodeAmount) {
        this.refferalCodeAmount = refferalCodeAmount;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getTellToFriendMessage() {
        return tellToFriendMessage;
    }

    public void setTellToFriendMessage(String tellToFriendMessage) {
        this.tellToFriendMessage = tellToFriendMessage;
    }

    public String getTellToFriendMessageSubject() {
        return tellToFriendMessageSubject;
    }

    public void setTellToFriendMessageSubject(String tellToFriendMessageSubject) {
        this.tellToFriendMessageSubject = tellToFriendMessageSubject;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}