package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class PriceData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_type")
    @Expose
    private String customerType;

    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("taxes")
    @Expose
    private Double taxes;
    @SerializedName("waypoint_from")
    @Expose
    private JSONObject waypoint_from;
    @SerializedName("waypoint_to")
    @Expose
    private JSONObject waypoint_to;
    @SerializedName("shipment_date")
    @Expose
    private String shipment_date;
    @SerializedName("expected_delivery_date")
    @Expose
    private String expected_delivery_date;
    @SerializedName("other_services")
    @Expose
    private List<OtherService> otherServices = null;

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public JSONObject getWaypoint_from() {
        return waypoint_from;
    }

    public void setWaypoint_from(JSONObject waypoint_from) {
        this.waypoint_from = waypoint_from;
    }

    public JSONObject getWaypoint_to() {
        return waypoint_to;
    }

    public void setWaypoint_to(JSONObject waypoint_to) {
        this.waypoint_to = waypoint_to;
    }

    public List<OtherService> getOtherServices() {
        return otherServices;
    }

    public void setOtherServices(List<OtherService> otherServices) {
        this.otherServices = otherServices;
    }

    public String getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(String shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getExpected_delivery_date() {
        return expected_delivery_date;
    }

    public void setExpected_delivery_date(String expected_delivery_date) {
        this.expected_delivery_date = expected_delivery_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double gettaxes() {
        return taxes;
    }

    public void settaxes(Double taxes) {
        this.taxes = taxes;
    }
}
