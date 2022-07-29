package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarBranchTo {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_en")
    @Expose
    private String name_en;
    @SerializedName("gps_coordinates")
    @Expose
    private GpsCoordinates gpsCoordinates;
    @SerializedName("has_satha_service")
    @Expose
    private Boolean has_satha_service;

    public Boolean getHas_satha_service() {
        return has_satha_service;
    }

    public void setHas_satha_service(Boolean has_satha_service) {
        this.has_satha_service = has_satha_service;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GpsCoordinates getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(GpsCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }
}
