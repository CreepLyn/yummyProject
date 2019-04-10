package com.yummy.controller;

import com.yummy.businessLogicService.UserBLService;
import com.yummy.vo.RedpacketVO;
import com.yummy.vo.UserVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBLService userBLService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody JSONObject jsonObject){
        return userBLService.login(jsonObject.getString("email"), jsonObject.getString("password"));
    }

    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    public String getCode(@RequestBody JSONObject jsonObject){
        return userBLService.getCode(jsonObject.getString("email"));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody JSONObject jsonObject) {
        return userBLService.register(jsonObject.getString("email"), jsonObject.getString("password"), jsonObject.getString("type"));
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    @ResponseBody
    public UserVO getInfo(@RequestBody JSONObject jsonObject){
        return userBLService.getInfo(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/getRedPackge", method = RequestMethod.POST)
    @ResponseBody
    public List<RedpacketVO> getRedPackge(@RequestBody JSONObject jsonObject){
        return userBLService.getRedPackge(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(@RequestBody JSONObject jsonObject){
        return userBLService.modify(jsonObject.getString("username"), jsonObject.getString("email"), jsonObject.getString("telephone"), jsonObject.getString("password"));
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ResponseBody
    public Double recharge(@RequestBody JSONObject jsonObject){
        return userBLService.recharge(jsonObject.getString("username"), jsonObject.getString("money"));
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public Double withdraw(@RequestBody JSONObject jsonObject){
        return userBLService.withdraw(jsonObject.getString("username"), jsonObject.getString("money"));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout(@RequestBody JSONObject jsonObject){
        return userBLService.logout(jsonObject.getString("username"));
    }
}
