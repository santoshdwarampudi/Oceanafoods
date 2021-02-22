package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuProductsResponseData implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("retail_price")
    @Expose
    private String retailPrice;
    @SerializedName("offerprice")
    @Expose
    private String offerprice;
    @SerializedName("offerapply")
    @Expose
    private Boolean offerapply;
    @SerializedName("pr_quantity")
    @Expose
    private String prQuantity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pr_tax")
    @Expose
    private String prTax;
    @SerializedName("pr_shippingcharge")
    @Expose
    private String prShippingcharge;
    @SerializedName("imagepath")
    @Expose
    private String imagepath;
    @SerializedName("menudisplay_image")
    @Expose
    private String menuDisplayImage;
    @SerializedName("primages")
    @Expose
    private List<MenuProductsImagesResponse> primages = null;
    @SerializedName("description")
    @Expose
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getOfferprice() {
        return offerprice;
    }

    public void setOfferprice(String offerprice) {
        this.offerprice = offerprice;
    }

    public Boolean getOfferapply() {
        return offerapply;
    }

    public void setOfferapply(Boolean offerapply) {
        this.offerapply = offerapply;
    }

    public String getPrQuantity() {
        return prQuantity;
    }

    public void setPrQuantity(String prQuantity) {
        this.prQuantity = prQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrTax() {
        return prTax;
    }

    public void setPrTax(String prTax) {
        this.prTax = prTax;
    }

    public String getPrShippingcharge() {
        return prShippingcharge;
    }

    public void setPrShippingcharge(String prShippingcharge) {
        this.prShippingcharge = prShippingcharge;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public List<MenuProductsImagesResponse> getPrimages() {
        return primages;
    }

    public void setPrimages(List<MenuProductsImagesResponse> primages) {
        this.primages = primages;
    }

    public String getMenuDisplayImage() {
        return menuDisplayImage;
    }

    public void setMenuDisplayImage(String menuDisplayImage) {
        this.menuDisplayImage = menuDisplayImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
