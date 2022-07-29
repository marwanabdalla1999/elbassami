package com.albassami.logistics.ui.Adapter;

public class towing_servicemodel {
    int Img;
    int name;
    price price;

    public towing_servicemodel(int img, int name, price price) {
        Img = img;
        this.name = name;
        this.price = price;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public com.albassami.logistics.ui.Adapter.price getPrice() {
        return price;
    }

    public void setPrice(com.albassami.logistics.ui.Adapter.price price) {
        this.price = price;
    }
}
