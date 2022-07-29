package com.albassami.logistics.ui.Adapter;

public class price {
    double startwith;
    double distanceprice;
    double timeprice;

    public price(double startwith, double distanceprice, double timeprice) {
        this.startwith = startwith;
        this.distanceprice = distanceprice;
        this.timeprice = timeprice;
    }

    public double getStartwith() {
        return startwith;
    }

    public void setStartwith(double startwith) {
        this.startwith = startwith;
    }

    public double getDistanceprice() {
        return distanceprice;
    }

    public void setDistanceprice(double distanceprice) {
        this.distanceprice = distanceprice;
    }

    public double getTimeprice() {
        return timeprice;
    }

    public void setTimeprice(double timeprice) {
        this.timeprice = timeprice;
    }
}
