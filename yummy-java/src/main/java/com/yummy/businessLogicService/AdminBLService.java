package com.yummy.businessLogicService;

import com.yummy.vo.AdminVO;
import com.yummy.vo.SettlementVO;
import com.yummy.vo.ShopVO;

import java.util.List;

public interface AdminBLService {

    AdminVO getInfo();

    List<ShopVO> getCheckingList();

    String checkShop(String id, String result);

    SettlementVO getSettleInfo();

    String settle(String orderid, String profit);

}
