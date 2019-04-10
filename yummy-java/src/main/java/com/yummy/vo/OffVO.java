package com.yummy.vo;

public class OffVO {

    private String off;
    private String price;

    public OffVO() {
    }

    public OffVO(String off, String price) {
        this.off = off;
        this.price = price;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
