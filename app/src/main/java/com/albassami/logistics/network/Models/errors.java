package com.albassami.logistics.network.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class errors {
    @SerializedName("mobile")
    @Expose
   private String mobile;

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile;
    }
}
