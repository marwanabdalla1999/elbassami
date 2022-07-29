package com.albassami.logistics.dto.response;

import com.google.android.gms.maps.model.LatLng;

public class branchesdata {
    String name;
    LatLng location;

    public branchesdata(String name, LatLng location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
