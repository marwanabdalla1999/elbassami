package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TowingDataResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("towing_type")
    @Expose
    private String towingType;
    @SerializedName("price")
    @Expose
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTowingType() {
        return towingType;
    }

    public void setTowingType(String towingType) {
        this.towingType = towingType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
