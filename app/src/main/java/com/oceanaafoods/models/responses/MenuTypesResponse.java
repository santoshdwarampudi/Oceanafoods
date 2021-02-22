package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuTypesResponse implements Serializable {
    @SerializedName("menutypes")
    @Expose
    private List<MenuTypeResponseData> menutypes = null;

    public List<MenuTypeResponseData> getMenutypes() {
        return menutypes;
    }

    public void setMenutypes(List<MenuTypeResponseData> menutypes) {
        this.menutypes = menutypes;
    }
}
