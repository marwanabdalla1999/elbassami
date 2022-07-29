package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOrderResponseList {
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("customer_id")
    @Expose
    private Integer customer_id;
    @SerializedName("order_ref")
    @Expose
    private String orderRef;

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

}