package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataResponse {
    @SerializedName("regions")
    @Expose
    private ArrayList<RegionBrnaches> regions = null;
    @SerializedName("pricelists")
    @Expose
    private ArrayList<PriceList> pricelists = null;
    @SerializedName("shipment_types")
    @Expose
    private ArrayList<ShipmentType> shipmentTypes = null;

    @SerializedName("car_makers")
    @Expose
    private ArrayList<CarMaker> carMakers = null;


    @SerializedName("vehicle_colors")
    @Expose
    private ArrayList<VehicleColor> vehicleColors = null;

    @SerializedName("plate_types")
    @Expose
    private ArrayList<plate_types> plate_types = null;
    @SerializedName("id_card_types")
    @Expose
     List<Object> id_card_types = null;

    @SerializedName("car_years")
    @Expose
    private ArrayList<CarYear> carYears = null;


    @SerializedName("countries")
    @Expose
    private ArrayList<CountriesData> countries = null;


    public List<Object> getId_card_types() {
        return  id_card_types;
    }

    public void setId_card_types(ArrayList<Object> id_card_types) {
        this.id_card_types = id_card_types;
    }
    public ArrayList<CarMaker> getCarMakers() {
        return carMakers;
    }

    public void setCarMakers(ArrayList<CarMaker> carMakers) {
        this.carMakers = carMakers;
    }

    public ArrayList<PriceList> getPricelists() {
        return pricelists;
    }

    public void setPricelists(ArrayList<PriceList> pricelists) {
        this.pricelists = pricelists;
    }

    public ArrayList<ShipmentType> getShipmentTypes() {
        return shipmentTypes;
    }

    public void setShipmentTypes(ArrayList<ShipmentType> shipmentTypes) {
        this.shipmentTypes = shipmentTypes;
    }

    public ArrayList<RegionBrnaches> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<RegionBrnaches> regions) {
        this.regions = regions;
    }


    public ArrayList<VehicleColor> getVehicleColors() {
        return vehicleColors;
    }

    public void setVehicleColors(ArrayList<VehicleColor> vehicleColors) {
        this.vehicleColors = vehicleColors;
    }

    public ArrayList<CarYear> getCarYears() {
        return carYears;
    }

    public void setCarYears(ArrayList<CarYear> carYears) {
        this.carYears = carYears;
    }

    public ArrayList<CountriesData> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<CountriesData> regions) {
        this.countries = countries;
    }
    public ArrayList<com.albassami.logistics.dto.response.plate_types> getPlate_types() {
        return plate_types;
    }

    public void setPlate_types(ArrayList<com.albassami.logistics.dto.response.plate_types> plate_types) {
        this.plate_types = plate_types;
    }


}
