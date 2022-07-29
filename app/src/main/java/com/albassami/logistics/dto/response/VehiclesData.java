package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

public class VehiclesData {
    @SerializedName("car_id")
    @Expose
    private Integer car_id;
    @SerializedName("plate_number")
    @Expose
    private String plateNumber;
    @SerializedName("plate_type")
    @Expose
    private String plateType;
    @SerializedName("model_id")
    @Expose
    private Integer modelId;
    @SerializedName("vehicle_maker_id")
    @Expose
    private Integer vehicleMakerId;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("maker_name")
    @Expose
    private String makerName;
    @SerializedName("type_name")
    @Expose
    private String typeName;
    @SerializedName("vehicle_type_id")
    @Expose
    private Integer vehicleTypeId;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("id_number")
    @Expose
    private String idNumber;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("owner_type")
    @Expose
    private String ownerType;
    @SerializedName("owner_id_type")
    @Expose
    private String ownerIdType;
    @SerializedName("owner_nationality")
    @Expose
    private String ownerNationality;
    @SerializedName("car_color")
    @Expose
    private String carColor;
    @SerializedName("car_year")
    @Expose
    private String carYear;

    public Integer getCar_id() {
        return car_id;
    }

    public void setCar_id(Integer car_id) {
        this.car_id = car_id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getVehicleMakerId() {
        return vehicleMakerId;
    }

    public void setVehicleMakerId(Integer vehicleMakerId) {
        this.vehicleMakerId = vehicleMakerId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerIdType() {
        return ownerIdType;
    }

    public void setOwnerIdType(String ownerIdType) {
        this.ownerIdType = ownerIdType;
    }

    public String getOwnerNationality() {
        return ownerNationality;
    }

    public void setOwnerNationality(String ownerNationality) {
        this.ownerNationality = ownerNationality;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }




}