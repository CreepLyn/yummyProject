package com.yummy.vo;

public class AddressVO {

    private Long id;
    private String description;
    private String name;
    private String sex;
    private String telephone;
    private boolean isValid;

    public AddressVO(Long id, String description, String name, String sex, String telephone) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.sex = sex;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
