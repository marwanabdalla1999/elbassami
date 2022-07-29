package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TowingResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<TowingDataResponse> data = null;

    public ArrayList<TowingDataResponse> getData() {
        return data;
    }

    public void setData(ArrayList<TowingDataResponse> data) {
        this.data = data;
    }

}
