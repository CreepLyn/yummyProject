package com.yummy.modal;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "shop")
public class Shop {

    private Long id ;
    private Long userid;
    private String shopname;
    private String address ;
    private String start_time;
    private String end_time;
    private String telephone;
    private Long mini_price;
    private Double delivery_price;
    private String state;
    private Double packing_price;
    private String type;
    private String description;
    private Map<String,String> off = new HashMap<String,String>();


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "userid")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userId) {
        this.userid = userId;
    }

    @Column(name = "shopname")
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "start_time")
    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @Column(name = "end_time")
    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "mini_price")
    public Long getMini_price() {
        return mini_price;
    }

    public void setMini_price(Long mini_price) {
        this.mini_price = mini_price;
    }

    @Column(name = "delivery_price")
    public Double getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(Double delivery_price) {
        this.delivery_price = delivery_price;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "packing_price")
    public Double getPacking_price() {
        return packing_price;
    }

    public void setPacking_price(Double packing_price) {
        this.packing_price = packing_price;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @ElementCollection
//    @CollectionTable(name="shop")
//    @MapKeyColumn(name="offKey")
//    @Column(name="offValue")
    @ElementCollection
    @JoinTable(name="off", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn (name="off_id")
    @Column(name="value")
    public Map<String, String> getOff() {
        return off;
    }

    public void setOff(Map<String, String> off) {
        this.off = off;
    }
}
