package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OffersResponse implements Serializable {
    @SerializedName("sliders")
    @Expose
    private List<OffersResponseData> sliders = null;
    @SerializedName("imagepath")
    @Expose
    private String imagepath;

    public List<OffersResponseData> getSliders() {
        return sliders;
    }

    public void setSliders(List<OffersResponseData> sliders) {
        this.sliders = sliders;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
