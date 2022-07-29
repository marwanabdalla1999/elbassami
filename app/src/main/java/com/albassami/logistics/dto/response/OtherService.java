package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherService {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("taxes")
    @Expose
    private Double taxes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

}

