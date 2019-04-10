package com.yummy.controller;

import com.yummy.businessLogicService.OrderBLService;
import com.yummy.vo.OrderVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderBLService orderBLService;

    @RequestMapping(value = "/payOrder", method = RequestMethod.POST)
    @ResponseBody
    public String payOrder(@RequestBody JSONObject jsonObject){
        return orderBLService.payOrder(jsonObject.getString("username"),
                jsonObject.getString("shopid"),
                jsonObject.getString("total"),
                jsonObject.getString("addressid"),
                jsonObject.getString("off"),
                jsonObject.getString("state"));
    }

    @RequestMapping(value = "/saveFoodList", method = RequestMethod.POST)
    @ResponseBody
    public String saveFoodList(@RequestBody JSONObject jsonObject){
        return orderBLService.saveFoodList(jsonObject.getString("code"),jsonObject.getString("redid"), jsonObject.getJSONArray("foodList"));
    }

    @RequestMapping(value = "/doPayOrder", method = RequestMethod.POST)
    @ResponseBody
    public String doPayOrder(@RequestBody JSONObject jsonObject){
        return orderBLService.doPayOrder(jsonObject.getString("id"));
    }

    @RequestMapping(value = "/getAllOrderByUsername", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderVO> getAllOrderByUsername(@RequestBody JSONObject jsonObject){
        return orderBLService.getAllOrderByUsername(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/getAllOrderByMerchant", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderVO> getAllOrderByMerchant(@RequestBody JSONObject jsonObject){
        return orderBLService.getAllOrderByMerchant(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public String cancelOrder(@RequestBody JSONObject jsonObject){
        return orderBLService.cancelOrder(jsonObject.getString("id"));
    }

    @RequestMapping(value = "/checkOrder", method = RequestMethod.POST)
    @ResponseBody
    public String checkOrder(@RequestBody JSONObject jsonObject){
        return orderBLService.checkOrder(jsonObject.getString("id"));
    }

    @RequestMapping(value = "/receiveOrder", method = RequestMethod.POST)
    @ResponseBody
    public String receiveOrder(@RequestBody JSONObject jsonObject){
        return orderBLService.receiveOrder(jsonObject.getString("id"));
    }

    @RequestMapping(value = "/quitOrder", method = RequestMethod.POST)
    @ResponseBody
    public String quitOrder(@RequestBody JSONObject jsonObject){
        return orderBLService.quitOrder(jsonObject.getString("id"));
    }

}
