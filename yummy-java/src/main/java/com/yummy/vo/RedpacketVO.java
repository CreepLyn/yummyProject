package com.yummy.vo;

public class RedpacketVO {

    private Long id;
    private Long user_id;
    private Integer bonus;
    private Integer price;
    private String type;
    private boolean valid;

    public RedpacketVO(Long id, Long user_id, Integer bonus, Integer price, String type) {
        this.id = id;
        this.user_id = user_id;
        this.bonus = bonus;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
