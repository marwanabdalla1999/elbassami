package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarModel {
    @SerializedName("car_model_id")
    @Expose
    private CarModelId carModelId;
    @SerializedName("car_size")
    @Expose
    private CarSize carSize;

    public CarModelId getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(CarModelId carModelId) {
        this.carModelId = carModelId;
    }

    public CarSize getCarSize() {
        return carSize;
    }

    public void setCarSize(CarSize carSize) {
        this.carSize = carSize;
    }

}
