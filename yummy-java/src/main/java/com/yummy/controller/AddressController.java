package com.yummy.controller;

import com.yummy.businessLogicService.AddressBLService;
import com.yummy.vo.AddressVO;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressBLService addressBLService;

    @RequestMapping(value = "/getAllAddressByUsername", method = RequestMethod.POST)
    @ResponseBody
    public List<AddressVO> getAllAddressByUsername(@RequestBody JSONObject jsonObject){
        return addressBLService.getAllByUsername(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    @ResponseBody
    public List<AddressVO> addAddress(@RequestBody JSONObject jsonObject){
        return addressBLService.addAddress(jsonObject.getString("username"), jsonObject.getString("name"), jsonObject.getString("telephone"), jsonObject.getString("sex"), jsonObject.getString("description"));
    }

    @RequestMapping(value = "/modifyAddress", method = RequestMethod.POST)
    @ResponseBody
    public String modifyAddress(@RequestBody JSONObject jsonObject){
        return addressBLService.modifyAddress(new Long(jsonObject.getInt("id")), jsonObject.getString("name"), jsonObject.getString("telephone"), jsonObject.getString("sex"), jsonObject.getString("description"));
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    @ResponseBody
    public String deleteAddress(@RequestBody JSONObject jsonObject){
        return addressBLService.deleteAddress(new Long(jsonObject.getInt("id")));
    }

}
