package com.yummy.businessLogic;

import com.yummy.businessLogicService.AdminBLService;
import com.yummy.modal.Orders;
import com.yummy.modal.Settlement;
import com.yummy.modal.Shop;
import com.yummy.repository.OrderRepository;
import com.yummy.repository.SettlementRepository;
import com.yummy.repository.ShopRepository;
import com.yummy.repository.UserRepository;
import com.yummy.util.*;
import com.yummy.util.NumberMessage;
import com.yummy.vo.AdminVO;
import com.yummy.vo.SettlementVO;
import com.yummy.vo.ShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminBL implements AdminBLService {

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SettlementRepository settlementRepository;

    /**
     * 得到统计信息
     * @return 统计信息
     */
    @Override
    public AdminVO getInfo() {
        return new AdminVO(userRepository.getAllByType(TypeMessage.CONSUMER).size(),
                userRepository.getAllByType(TypeMessage.MERCHANT).size(),
                userRepository.getAllByType(TypeMessage.LOGOUT).size(),
                shopRepository.getAllByType(TypeMessage.FAST).size(),
                shopRepository.getAllByType(TypeMessage.FOOD).size(),
                shopRepository.getAllByType(TypeMessage.DRINK).size(),
                shopRepository.getAllByType(TypeMessage.FRUIT).size());
    }

    /**
     * 得到待审核的商店列表
     * @return 商店列表
     */
    @Override
    public List<ShopVO> getCheckingList() {
        List<Shop> shopList = shopRepository.getAllByState(StateMessage.CHECK);
        List<ShopVO> shopVOList = new ArrayList<ShopVO>();
        for (Shop shopItem: shopList) {
            shopVOList.add(ConvertUtil.toShopVO(shopItem));
        }
        return shopVOList;
    }

    /**
     * 审核商店
     * @param id 商店ID
     * @param result 审核结果
     * @return 操作结果
     */
    @Override
    public String checkShop(String id, String result) {
        System.out.println(id+ " " + result);
        Shop shop = shopRepository.findShopById(Long.parseLong(id));
        shop.setState(result);
        shopRepository.save(shop);
        return ResultMessage.SUCCESS;
    }

    /**
     * 得到结算信息
     * @return 结算信息
     */
    @Override
    public SettlementVO getSettleInfo() {
        int id = settlementRepository.findAll().size();
        Settlement settlement = settlementRepository.getSettlementById((long) id);
        SettlementVO settlementVO = ConvertUtil.toSettlementVO(settlement);
        List<Orders> orderList = orderRepository.getAllByIdAfter(settlement.getOrderid());
        double orderMoney = 0.0;
        double profit = 0.0;
        for (Orders ordersItem: orderList) {
            Double total = ordersItem.getPrice();
            if (total > NumberMessage.priceLimit) {
                profit += total * NumberMessage.profitPercent;
            } else {
                profit += NumberMessage.profitLimit;
            }
            orderMoney += total;
        }
        settlementVO.setOrderNum(orderList.size());
        settlementVO.setOrderMoney(orderMoney);
        settlementVO.setProfit(profit);
        return settlementVO;
    }

    /**
     * 结算
     * @param orderid 上一次结算最后的订单ID
     * @param profit 期间利润
     * @return 操作结果
     */
    @Override
    public String settle(String orderid, String profit) {
        Date date = new Date();
        Settlement settlement = new Settlement();
        settlement.setDate(sdf.format(date));
        settlement.setOrderid(Long.parseLong(orderid));
        settlement.setProfit(Double.parseDouble(profit));
        settlementRepository.save(settlement);
        return sdf.format(date);
    }
}
