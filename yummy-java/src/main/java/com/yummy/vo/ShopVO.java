package com.yummy.vo;

import java.util.List;

public class ShopVO {
    private Long id ;
    private Long userid;
    private String shopname;
    private String address ;
    private String startTime;
    private String endTime;
    private String telephone;
    private Long miniPrice;
    private Double deliveryPrice;
    private String state;
    private Double packingPrice;
    private String url;
    private String type;
    private String description;
    private List<OffVO> offList;
    private List<MenuVO> menuList;

    public ShopVO(){}

    public ShopVO(Long id, Long userid, String shopname, String address, String startTime, String endTime, String telephone, Long miniPrice,Double deliveryPrice, String state, Double packingPrice, String type, String description){
        this.id = id;
        this.userid = userid;
        this.shopname = shopname;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.telephone = telephone;
        this.miniPrice = miniPrice;
        this.deliveryPrice = deliveryPrice;
        this.state = state;
        this.packingPrice = packingPrice;
        this.type = type;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userId) {
        this.userid = userId;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getMiniPrice() {
        return miniPrice;
    }

    public void setMiniPrice(Long miniPrice) {
        this.miniPrice = miniPrice;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPackingPrice() {
        return packingPrice;
    }

    public void setPackingPrice(Double packingPrice) {
        this.packingPrice = packingPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuVO> menuList) {
        this.menuList = menuList;
    }

    public List<OffVO> getOffList() {
        return offList;
    }

    public void setOffList(List<OffVO> offList) {
        this.offList = offList;
    }
}
