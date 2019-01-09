
package com.asi.yalla_passanger_eg.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarModelsModel {

    @SerializedName("ModelId")
    @Expose
    private String modelId;
    @SerializedName("ModelName")
    @Expose
    private String modelName;
    @SerializedName("BaseFare")
    @Expose
    private String baseFare;
    @SerializedName("MinKM")
    @Expose
    private String minKM;
    @SerializedName("MinFare")
    @Expose
    private String minFare;
    @SerializedName("CancellationFare")
    @Expose
    private String cancellationFare;
    @SerializedName("TaxiMinSpeed")
    @Expose
    private String taxiMinSpeed;
    @SerializedName("NightFare")
    @Expose
    private String nightFare;
    @SerializedName("NightCharge")
    @Expose
    private String nightCharge;
    @SerializedName("NightTimingFrom")
    @Expose
    private String nightTimingFrom;
    @SerializedName("NightTimingTo")
    @Expose
    private String nightTimingTo;
    @SerializedName("EveningCharge")
    @Expose
    private String eveningCharge;
    @SerializedName("EveningFare")
    @Expose
    private String eveningFare;
    @SerializedName("EveningTimingFrom")
    @Expose
    private String eveningTimingFrom;
    @SerializedName("EveningTimingTo")
    @Expose
    private String eveningTimingTo;
    @SerializedName("WaitingTime")
    @Expose
    private String waitingTime;
    @SerializedName("MinutesFare")
    @Expose
    private String minutesFare;
    @SerializedName("ModelSize")
    @Expose
    private String modelSize;
    @SerializedName("AboveKmRange")
    @Expose
    private String aboveKmRange;
    @SerializedName("belowKmRange")
    @Expose
    private String belowKmRange;

    boolean selected;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getMinKM() {
        return minKM;
    }

    public void setMinKM(String minKM) {
        this.minKM = minKM;
    }

    public String getMinFare() {
        return minFare;
    }

    public void setMinFare(String minFare) {
        this.minFare = minFare;
    }

    public String getCancellationFare() {
        return cancellationFare;
    }

    public void setCancellationFare(String cancellationFare) {
        this.cancellationFare = cancellationFare;
    }

    public String getTaxiMinSpeed() {
        return taxiMinSpeed;
    }

    public void setTaxiMinSpeed(String taxiMinSpeed) {
        this.taxiMinSpeed = taxiMinSpeed;
    }

    public String getNightFare() {
        return nightFare;
    }

    public void setNightFare(String nightFare) {
        this.nightFare = nightFare;
    }

    public String getNightCharge() {
        return nightCharge;
    }

    public void setNightCharge(String nightCharge) {
        this.nightCharge = nightCharge;
    }

    public String getNightTimingFrom() {
        return nightTimingFrom;
    }

    public void setNightTimingFrom(String nightTimingFrom) {
        this.nightTimingFrom = nightTimingFrom;
    }

    public String getNightTimingTo() {
        return nightTimingTo;
    }

    public void setNightTimingTo(String nightTimingTo) {
        this.nightTimingTo = nightTimingTo;
    }

    public String getEveningCharge() {
        return eveningCharge;
    }

    public void setEveningCharge(String eveningCharge) {
        this.eveningCharge = eveningCharge;
    }

    public String getEveningFare() {
        return eveningFare;
    }

    public void setEveningFare(String eveningFare) {
        this.eveningFare = eveningFare;
    }

    public String getEveningTimingFrom() {
        return eveningTimingFrom;
    }

    public void setEveningTimingFrom(String eveningTimingFrom) {
        this.eveningTimingFrom = eveningTimingFrom;
    }

    public String getEveningTimingTo() {
        return eveningTimingTo;
    }

    public void setEveningTimingTo(String eveningTimingTo) {
        this.eveningTimingTo = eveningTimingTo;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getMinutesFare() {
        return minutesFare;
    }

    public void setMinutesFare(String minutesFare) {
        this.minutesFare = minutesFare;
    }

    public String getModelSize() {
        return modelSize;
    }

    public void setModelSize(String modelSize) {
        this.modelSize = modelSize;
    }

    public String getAboveKmRange() {
        return aboveKmRange;
    }

    public void setAboveKmRange(String aboveKmRange) {
        this.aboveKmRange = aboveKmRange;
    }

    public String getBelowKmRange() {
        return belowKmRange;
    }

    public void setBelowKmRange(String belowKmRange) {
        this.belowKmRange = belowKmRange;
    }


    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
