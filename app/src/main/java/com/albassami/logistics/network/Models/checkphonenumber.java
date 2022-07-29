package com.albassami.logistics.network.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class checkphonenumber {
    @SerializedName("errors")
    @Expose
    private ArrayList<errors> errors;

    public ArrayList geterrors() {
        return errors;
    }

    public void seterrors(ArrayList errors) {
        this.errors = errors;
    }
}
