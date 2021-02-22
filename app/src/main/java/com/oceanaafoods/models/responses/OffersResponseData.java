package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OffersResponseData implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mtype")
    @Expose
    private String mtype;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("imserted_date")
    @Expose
    private String imsertedDate;
    @SerializedName("priority")
    @Expose
    private String priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImsertedDate() {
        return imsertedDate;
    }

    public void setImsertedDate(String imsertedDate) {
        this.imsertedDate = imsertedDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
