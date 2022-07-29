package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class type {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("vehicle_type_name")
    @Expose
    private String vehicle_type_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicle_type_name() {
        return vehicle_type_name;
    }

    public void setVehicle_type_name(String vehicle_type_name) {
        this.vehicle_type_name = vehicle_type_name;
    }
}
