package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItemResponseData implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("menutypeid")
    @Expose
    private String menutypeid;
    @SerializedName("categoryname")
    @Expose
    private String categoryname;
    @SerializedName("adminid")
    @Expose
    private String adminid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hidden")
    @Expose
    private String hidden;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenutypeid() {
        return menutypeid;
    }

    public void setMenutypeid(String menutypeid) {
        this.menutypeid = menutypeid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }
}
