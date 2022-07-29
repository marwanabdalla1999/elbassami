package com.albassami.logistics.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegionBrnaches {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_ar")
    @Expose
    private String name_ar;
    @SerializedName("branches")
    @Expose
    private ArrayList<Branches> branches = null;


    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Branches> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Branches> branches) {
        this.branches = branches;
    }
}