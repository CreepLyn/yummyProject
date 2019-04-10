package com.yummy.controller;

import com.yummy.businessLogicService.AdminBLService;
import com.yummy.vo.AdminVO;
import com.yummy.vo.SettlementVO;
import com.yummy.vo.ShopVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminBLService adminBLService;

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public AdminVO getInfo(){
        return adminBLService.getInfo();
    }

    @RequestMapping(value = "/getCheckingList", method = RequestMethod.GET)
    @ResponseBody
    public List<ShopVO> getCheckingList(){
        return adminBLService.getCheckingList();
    }

    @RequestMapping(value = "/checkShop", method = RequestMethod.POST)
    @ResponseBody
    public String checkShop(@RequestBody JSONObject jsonObject){
        return adminBLService.checkShop(jsonObject.getString("id"), jsonObject.getString("result"));
    }

    @RequestMapping(value = "/getSettleInfo", method = RequestMethod.GET)
    @ResponseBody
    public SettlementVO getSettleInfo(){
        return adminBLService.getSettleInfo();
    }

    @RequestMapping(value = "/settle", method = RequestMethod.POST)
    @ResponseBody
    public String settle(@RequestBody JSONObject jsonObject){
        return adminBLService.settle(jsonObject.getString("orderid"), jsonObject.getString("profit"));
    }

}
