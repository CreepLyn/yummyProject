package com.yummy.vo;

public class FoodVO {

    private Long id ;
    private Long menuid;
    private String name;
    private Integer num;
    private Double price;
    private String url;
    private int menge;

    public FoodVO(){}

    public FoodVO(Long id, Long menuid, String name, Integer num, Double price){
        this.id = id;
        this.menuid = menuid;
        this.name = name;
        this.num = num;
        this.price = price;
        this.menge = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public void setMenuid(Long menuid) {

        this.menuid = menuid;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
