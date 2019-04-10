package com.yummy.util;

import com.yummy.modal.*;
import com.yummy.vo.*;

import java.util.ArrayList;

public class ConvertUtil {

    private static String path = System.getProperty("user.dir") + "/pictures/shops";

    public static UserVO toUserVO(User user){
        return new UserVO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBalance(),
                user.getType(),
                user.getTelephone(),
                user.getLevel());
    }

    public static ShopVO toShopVO(Shop shop){
        ShopVO shopVO = new ShopVO(shop.getId(),
                shop.getUserid(),
                shop.getShopname(),
                shop.getAddress(),
                shop.getStart_time(),
                shop.getEnd_time(),
                shop.getTelephone(),
                shop.getMini_price(),
                shop.getDelivery_price(),
                shop.getState(),
                shop.getPacking_price(),
                shop.getType(),
                shop.getDescription());
        shopVO.setUrl(PictureUtil.imageChangeBase64(path + "/" + shop.getShopname() + "/" + "cover.jpg"));
        return shopVO;
    }

    public static MenuVO toMenuVO(Menu menu) {
        return new MenuVO(menu.getId(), menu.getName(), new ArrayList<FoodVO>());
    }

    public static FoodVO toFoodVO(Food food){
        return new FoodVO(food.getId(),
                food.getMenuid(),
                food.getName(),
                food.getNum(),
                food.getPrice());
    }

    public static AddressVO toAddressVO(Address address){
        return new AddressVO(address.getId(),
                address.getDescription(),
                address.getName(),
                address.getSex(),
                address.getTelephone());
    }

    public static TrackVO toTrackVO(Track track){
        return new TrackVO(track.getPlace_time(),
                track.getPay_time(),
                track.getReceive_time(),
                track.getDeliver_time(),
                track.getArrive_time());
    }

    public static OrderVO toOrderVO(Orders orders){
        return new OrderVO(orders.getId(),
                orders.getCode(),
                orders.getDate(),
                orders.getState(),
                orders.getPrice(),
                orders.getOff(),
                orders.getRedoff());
    }

    public static SettlementVO toSettlementVO(Settlement settlement){
        return new SettlementVO(settlement.getOrderid(), settlement.getDate());
    }

    public static RedpacketVO toRedpacketVO(Redpacket redpacket) {
        return new RedpacketVO(redpacket.getId(),
                redpacket.getUserid(),
                redpacket.getBonus(),
                redpacket.getPrice(),
                redpacket.getType());
    }

}
