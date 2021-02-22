package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuItemsResponse implements Serializable {
    @SerializedName("menuitems")
    @Expose
    private List<MenuItemResponseData> menuitems = null;

    public List<MenuItemResponseData> getMenuitems() {
        return menuitems;
    }

    public void setMenuitems(List<MenuItemResponseData> menuitems) {
        this.menuitems = menuitems;
    }
}
