package com.yummy.businessLogicService;

import com.yummy.vo.OrderVO;
import net.sf.json.JSONArray;

import java.util.List;

public interface OrderBLService {

    String payOrder(String username, String shopid, String total, String addressid, String off, String state);

    String saveFoodList(String code, String redid, JSONArray foodList);

    String doPayOrder(String id);

    List<OrderVO> getAllOrderByUsername(String username);

    List<OrderVO> getAllOrderByMerchant(String username);

    String cancelOrder(String id);

    String checkOrder(String id);

    String receiveOrder(String id);

    String quitOrder(String id);

}
