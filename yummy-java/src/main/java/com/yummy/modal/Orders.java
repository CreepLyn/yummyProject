package com.yummy.modal;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders")
public class Orders {

    private Long id;
    private String code;
    private Long userid;
    private Long shopid;
    private String date;
    private Double price;
    private String state;
    private Integer off;
    private Integer redoff;
    private Long expressmanid;
    private Long addressid;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "userid")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Column(name = "shopid")
    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "off")
    public Integer getOff() {
        return off;
    }

    public void setOff(Integer off) {
        this.off = off;
    }

    @Column(name = "redoff")
    public Integer getRedoff() {
        return redoff;
    }

    public void setRedoff(Integer redoff) {
        this.redoff = redoff;
    }

    @Column(name = "expressmanid")
    public Long getExpressmanid() {
        return expressmanid;
    }

    public void setExpressmanid(Long expressmanid) {
        this.expressmanid = expressmanid;
    }

    @Column(name = "addressid")
    public Long getAddressid() {
        return addressid;
    }

    public void setAddressid(Long addressid) {
        this.addressid = addressid;
    }

}
