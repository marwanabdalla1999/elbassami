package com.albassami.logistics.network.Models;

import com.google.gson.JsonObject;

/**
 * Created by Mahesh on 1/20/2017.
 */

public class History {
    private String sale_line_rec_name, plate_no, state;
    private String loc_from, loc_to;

    public String getSale_line_rec_name() {
        return sale_line_rec_name;
    }

    public void setSale_line_rec_name(String sale_line_rec_name) {
        this.sale_line_rec_name = sale_line_rec_name;
    }

    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }

    public String getLoc_from() {
        return loc_from;
    }

    public void setLoc_from(String loc_from) {
        this.loc_from = loc_from;
    }

    public String getLoc_to() {
        return loc_to;
    }

    public void setLoc_to(String loc_to) {
        this.loc_to = loc_to;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}