package com.yummy.vo;

import java.util.List;

public class OrderVO {

    private Long id;
    private String code;
    private String username;
    private ShopVO shop;
    private String date;
    private Double price;
    private String state;
    private Integer off;
    private Integer redoff;
    private Long expressmanid;
    private AddressVO address;
    private List<FoodVO> foodList;
    private List<StateVO> stateList;

    public OrderVO(Long id, String code, String date, String state, Double price, Integer off, Integer redoff){
        this.id = id;
        this.code = code;
        this.date = date;
        this.state = state;
        this.price = price;
        this.off = off;
        this.redoff = redoff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ShopVO getShop() {
        return shop;
    }

    public void setShop(ShopVO shop) {
        this.shop = shop;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getExpressmanid() {
        return expressmanid;
    }

    public void setExpressmanid(Long expressmanid) {
        this.expressmanid = expressmanid;
    }

    public AddressVO getAddress() {
        return address;
    }

    public void setAddress(AddressVO address) {
        this.address = address;
    }

    public List<FoodVO> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodVO> foodList) {
        this.foodList = foodList;
    }

    public Integer getOff() {
        return off;
    }

    public void setOff(Integer off) {
        this.off = off;
    }

    public Integer getRedoff() {
        return redoff;
    }

    public void setRedoff(Integer redoff) {
        this.redoff = redoff;
    }

    public List<StateVO> getStateList() {
        return stateList;
    }

    public void setStateList(List<StateVO> stateList) {
        this.stateList = stateList;
    }
}
