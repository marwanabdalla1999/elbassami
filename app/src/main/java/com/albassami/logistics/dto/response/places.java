package com.albassami.logistics.dto.response;

import com.google.android.gms.maps.model.LatLng;

public class places {
    LatLng latLng;
    String place_id;

    public places(LatLng latLng, String place_id) {
        this.latLng = latLng;
        this.place_id = place_id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
