package com.yummy.vo;

public class AdminVO {

    private int cosNum;
    private int merNum;
    private int outNum;
    private int fastNum;
    private int foodNum;
    private int drinkNum;
    private int fruitNum;

    public AdminVO(int cosNum, int merNum, int outNum, int fastNum, int foodNum, int drinkNum, int fruitNum) {
        this.cosNum = cosNum;
        this.merNum = merNum;
        this.outNum = outNum;
        this.fastNum = fastNum;
        this.foodNum = foodNum;
        this.drinkNum = drinkNum;
        this.fruitNum = fruitNum;
    }

    public int getCosNum() {
        return cosNum;
    }

    public void setCosNum(int cosNum) {
        this.cosNum = cosNum;
    }

    public int getMerNum() {
        return merNum;
    }

    public void setMerNum(int merNum) {
        this.merNum = merNum;
    }

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }

    public int getFastNum() {
        return fastNum;
    }

    public void setFastNum(int fastNum) {
        this.fastNum = fastNum;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public int getDrinkNum() {
        return drinkNum;
    }

    public void setDrinkNum(int drinkNum) {
        this.drinkNum = drinkNum;
    }

    public int getFruitNum() {
        return fruitNum;
    }

    public void setFruitNum(int fruitNum) {
        this.fruitNum = fruitNum;
    }
}
