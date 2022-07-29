package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetVehiclesResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<VehiclesData> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<VehiclesData> getData() {
        return data;
    }

    public void setData(ArrayList<VehiclesData> data) {
        this.data = data;
    }}