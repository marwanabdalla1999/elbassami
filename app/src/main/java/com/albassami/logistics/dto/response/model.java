package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class model {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("car_maker_id")
    @Expose
    private String car_maker_id;

    @SerializedName("car_model_name")
    @Expose
    private String car_model_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_maker_id() {
        return car_maker_id;
    }

    public void setCar_maker_id(String car_maker_id) {
        this.car_maker_id = car_maker_id;
    }

    public String getCar_model_name() {
        return car_model_name;
    }

    public void setCar_model_name(String car_model_name) {
        this.car_model_name = car_model_name;
    }
}
