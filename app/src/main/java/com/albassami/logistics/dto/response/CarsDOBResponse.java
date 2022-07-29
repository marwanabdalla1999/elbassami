package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CarsDOBResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private ArrayList<CarDOBData> data = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<CarDOBData> getData() {
        return data;
    }

    public void setData(ArrayList<CarDOBData> data) {
        this.data = data;
    }
}
