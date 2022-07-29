package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class maker {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String car_make_ar_name;
    @SerializedName("name_ar")
    @Expose
    private String name_ar;

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_make_ar_name() {
        return car_make_ar_name;
    }

    public void setCar_make_ar_name(String car_make_ar_name) {
        this.car_make_ar_name = car_make_ar_name;
    }
}
