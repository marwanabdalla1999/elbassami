package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleColor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vehicle_color_name")
    @Expose
    private String vehicle_color_name;
    @SerializedName("vehicle_color_name_en")
    @Expose
    private String vehicle_color_name_en;

    public String getVehicle_color_name_en() {
        return vehicle_color_name_en;
    }

    public void setVehicle_color_name_en(String vehicle_color_name_en) {
        this.vehicle_color_name_en = vehicle_color_name_en;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicle_color_name() {
        return vehicle_color_name;
    }

    public void setVehicle_color_name(String vehicle_color_name) {
        this.vehicle_color_name = vehicle_color_name;
    }


}
