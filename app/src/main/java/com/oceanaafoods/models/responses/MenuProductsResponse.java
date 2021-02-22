package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuProductsResponse implements Serializable {

    @SerializedName("getproducts")
    @Expose
    private List<MenuProductsResponseData> getproducts = null;

    public List<MenuProductsResponseData> getGetproducts() {
        return getproducts;
    }

    public void setGetproducts(List<MenuProductsResponseData> getproducts) {
        this.getproducts = getproducts;
    }
}
