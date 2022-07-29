package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPriceDataResponse {
    @SerializedName("data")
    @Expose
    private List<DataResponse> data = null;

    public List<DataResponse> getData() {
        return data;
    }

    public void setData(List<DataResponse> data) {
        this.data = data;
    }
}
