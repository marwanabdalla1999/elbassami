package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PriceResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private List<PriceData> data = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PriceData> getData() {
        return data;
    }

    public void setData(List<PriceData> data) {
        this.data = data;
    }
}
