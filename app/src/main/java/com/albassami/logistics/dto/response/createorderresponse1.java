package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class createorderresponse1 {
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("data")
    @Expose
    private ArrayList<createorderresopne> data = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<createorderresopne> getData() {
        return data;
    }

    public void setData(ArrayList<createorderresopne> data) {
        this.data = data;
    }
}
