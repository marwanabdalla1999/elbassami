package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarDOBData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sale_line_rec_name")
    @Expose
    private String saleLineRecName;
    @SerializedName("loc_from")
    @Expose
    private CarBranchFrom locFrom;
    @SerializedName("loc_to")
    @Expose
    private CarBranchTo locTo;
    @SerializedName("state")
    @Expose
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleLineRecName() {
        return saleLineRecName;
    }

    public void setSaleLineRecName(String saleLineRecName) {
        this.saleLineRecName = saleLineRecName;
    }

    public CarBranchFrom getLocFrom() {
        return locFrom;
    }

    public void setLocFrom(CarBranchFrom locFrom) {
        this.locFrom = locFrom;
    }

    public CarBranchTo getLocTo() {
        return locTo;
    }

    public void setLocTo(CarBranchTo locTo) {
        this.locTo = locTo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
