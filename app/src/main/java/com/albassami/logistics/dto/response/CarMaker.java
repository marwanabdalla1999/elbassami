package com.albassami.logistics.dto.response;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CarMaker {
    @SerializedName("car_maker_id")
    @Expose
    private CarMakerId carMakerId;
    @SerializedName("car_models")
    @Expose
    private ArrayList<CarModel> carModels = null;

    public CarMakerId getCarMakerId() {
        return carMakerId;
    }

    public void setCarMakerId(CarMakerId carMakerId) {
        this.carMakerId = carMakerId;
    }

    public ArrayList<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(ArrayList<CarModel> carModels) {
        this.carModels = carModels;
    }
}
