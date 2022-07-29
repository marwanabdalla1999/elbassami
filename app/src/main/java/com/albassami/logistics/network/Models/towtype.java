package com.albassami.logistics.network.Models;

public class towtype {
    String baseprice;
    String km_price;
    String minute_price;
   String tow_trunck_id;
   String shippment_type_id;

    public towtype() {
        baseprice="";
        km_price="";
        minute_price="";
        tow_trunck_id="";
        shippment_type_id="";
    }

    public String getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(String baseprice) {
        this.baseprice = baseprice;
    }

    public String getKm_price() {
        return km_price;
    }

    public void setKm_price(String kn_price) {
        this.km_price = kn_price;
    }

    public String getMinute_price() {
        return minute_price;
    }

    public void setMinute_price(String minute_price) {
        this.minute_price = minute_price;
    }

    public String getTow_trunck_id() {
        return tow_trunck_id;
    }

    public void setTow_trunck_id(String tow_trunck_id) {
        this.tow_trunck_id = tow_trunck_id;
    }

    public String getShippment_type_id() {
        return shippment_type_id;
    }

    public void setShippment_type_id(String shippment_type_id) {
        this.shippment_type_id = shippment_type_id;
    }
}
