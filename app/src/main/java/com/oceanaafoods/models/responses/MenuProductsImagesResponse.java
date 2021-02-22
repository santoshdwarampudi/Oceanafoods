package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuProductsImagesResponse implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("prod_id")
    @Expose
    private String prodId;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_featured")
    @Expose
    private String isFeatured;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(String isFeatured) {
        this.isFeatured = isFeatured;
    }

}
