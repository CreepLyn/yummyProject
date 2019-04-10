package com.yummy.vo;


import java.util.List;

public class MenuVO {

    private Long id;
    private String name;
    private List<FoodVO> foodList;

    public MenuVO(){}

    public MenuVO(Long id, String name, List<FoodVO> foodList) {
        this.id = id;
        this.name = name;
        this.foodList = foodList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFoodList(List<FoodVO> foodList) {
        this.foodList = foodList;
    }

    public List<FoodVO> getFoodList() {
        return foodList;
    }
}
