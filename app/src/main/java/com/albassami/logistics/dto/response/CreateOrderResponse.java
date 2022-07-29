package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private ArrayList<CreateOrderResponseList> data = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<CreateOrderResponseList> getData() {
        return data;
    }

    public void setData(ArrayList<CreateOrderResponseList> data) {
        this.data = data;
    }

}
