package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScratchcardData implements Serializable {
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("uniqueid")
    @Expose
    private String uniqueid;
    @SerializedName("billnumber")
    @Expose
    private String billnumber;
    @SerializedName("billamount")
    @Expose
    private String billamount;
    @SerializedName("offertype")
    @Expose
    private String offertype;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("expired")
    @Expose
    private String expired;
    @SerializedName("fooditemid")
    @Expose
    private String fooditemid;
    @SerializedName("foodprice")
    @Expose
    private String foodprice;
    @SerializedName("foodimage")
    @Expose
    private String foodimage;
    @SerializedName("scratch_status")
    @Expose
    private String scratchStatus;
    @SerializedName("expirydate")
    @Expose
    private String expirydate;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }

    public String getBillamount() {
        return billamount;
    }

    public void setBillamount(String billamount) {
        this.billamount = billamount;
    }

    public String getOffertype() {
        return offertype;
    }

    public void setOffertype(String offertype) {
        this.offertype = offertype;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getFooditemid() {
        return fooditemid;
    }

    public void setFooditemid(String fooditemid) {
        this.fooditemid = fooditemid;
    }

    public String getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(String foodprice) {
        this.foodprice = foodprice;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getScratchStatus() {
        return scratchStatus;
    }

    public void setScratchStatus(String scratchStatus) {
        this.scratchStatus = scratchStatus;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }
}
