package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateOrderResponse2 {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private ArrayList<CreateOrderResponseList1> data = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<CreateOrderResponseList1> getData() {
        return data;
    }

    public void setData(ArrayList<CreateOrderResponseList1> data) {
        this.data = data;
    }

}
