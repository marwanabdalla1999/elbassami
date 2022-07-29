package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipmentType {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("car_shipment_name")
    @Expose
    private String carShipmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarShipmentName() {
        return carShipmentName;
    }

    public void setCarShipmentName(String carShipmentName) {
        this.carShipmentName = carShipmentName;
    }
}
