package com.oceanaafoods.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    @SerializedName("phoneremail")
    @Expose
    private String phoneremail;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;

    public String getPhoneremail() {
        return phoneremail;
    }

    public void setPhoneremail(String phoneremail) {
        this.phoneremail = phoneremail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
