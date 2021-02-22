package com.oceanaafoods.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ScratchCardResponse implements Serializable {
    @SerializedName("scratchcards")
    @Expose
    private List<ScratchcardData> scratchcards = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<ScratchcardData> getScratchcards() {
        return scratchcards;
    }

    public void setScratchcards(List<ScratchcardData> scratchcards) {
        this.scratchcards = scratchcards;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
