package com.yummy.vo;

import java.sql.Time;

public class TrackVO {

    private String place_time;
    private String pay_time;
    private String receive_time;
    private String deliver_time;
    private String arrive_time;

    public TrackVO(String place_time, String pay_time, String receive_time, String deliver_time, String arrive_time) {
        this.place_time = place_time;
        this.pay_time = pay_time;
        this.receive_time = receive_time;
        this.deliver_time = deliver_time;
        this.arrive_time = arrive_time;
    }

    public String getPlace_time() {
        return place_time;
    }

    public void setPlace_time(String place_time) {
        this.place_time = place_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }
}
