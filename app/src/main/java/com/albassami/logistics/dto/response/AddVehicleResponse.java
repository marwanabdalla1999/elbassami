package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVehicleResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private data data;


}
