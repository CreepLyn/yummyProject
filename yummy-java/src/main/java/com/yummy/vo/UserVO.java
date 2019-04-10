package com.yummy.vo;

public class UserVO {

    private Long id ;
    private String username;
    private String email;
    private Double balance;
    private String type;
    private String telephone;
    private String level;

    public UserVO(Long id, String username, String email, Double balance, String type, String telephone, String level) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.balance = balance;
        this.type = type;
        this.telephone = telephone;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
