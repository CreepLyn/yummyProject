package com.yummy.controller;

import com.yummy.businessLogicService.ShopBLService;
import com.yummy.vo.FoodVO;
import com.yummy.vo.MenuVO;
import com.yummy.vo.OffVO;
import com.yummy.vo.ShopVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopBLService shopBLService;

    @RequestMapping(value = "/getShopList", method = RequestMethod.GET)
    @ResponseBody
    public List<ShopVO> getShopList(){
        return shopBLService.getShopList();
    }

    @RequestMapping(value = "/getShopListByType", method = RequestMethod.POST)
    @ResponseBody
    public List<ShopVO> getShopListByType(@RequestBody JSONObject jsonObject){
        return shopBLService.getShopListByType(jsonObject.getString("type"));
    }

    @RequestMapping(value = "/searchShop", method = RequestMethod.POST)
    @ResponseBody
    public List<ShopVO> searchShop(@RequestBody JSONObject jsonObject){
        return shopBLService.searchShop(jsonObject.getString("text"));
    }

    @RequestMapping(value = "/getShopInfo", method = RequestMethod.POST)
    @ResponseBody
    public ShopVO getShopInfo(@RequestBody JSONObject jsonObject){
        return shopBLService.getShopInfo(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/getShopState", method = RequestMethod.POST)
    @ResponseBody
    public String getShopState(@RequestBody JSONObject jsonObject){
        return shopBLService.getShopState(jsonObject.getString("username"));
    }

    @RequestMapping(value = "/getMenuInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<MenuVO> getMenuInfo(@RequestBody JSONObject jsonObject){
        return shopBLService.getMenuInfo(jsonObject.getString("shopid"));
    }

    @RequestMapping(value = "/getOffInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<OffVO> getOffInfo(@RequestBody JSONObject jsonObject){
        return shopBLService.getOffInfo(jsonObject.getString("shopid"));
    }

    @RequestMapping(value = "/addShop", method = RequestMethod.POST)
    @ResponseBody
    public String addShop(@RequestParam("file") MultipartFile multipartFile, @RequestParam("shopname") String shopname,
                          @RequestParam("type") String type, @RequestParam("startTime") String startTime,
                          @RequestParam("endTime") String endTime, @RequestParam("address") String address,
                          @RequestParam("telephone") String telephone, @RequestParam("description") String description,
                          @RequestParam("miniPrice") String miniPrice, @RequestParam("deliveryPrice") String deliveryPrice,
                          @RequestParam("username") String username){
        return shopBLService.addShop(multipartFile, shopname, type, startTime, endTime, address, telephone, description, miniPrice, deliveryPrice, username);
    }

    @RequestMapping(value = "/modifyShop", method = RequestMethod.POST)
    @ResponseBody
    public String modifyShop(@RequestBody JSONObject jsonObject){
        return shopBLService.modifyShop(jsonObject.getString("shopname"),
                jsonObject.getString("type"),
                jsonObject.getString("startTime"),
                jsonObject.getString("endTime"),
                jsonObject.getString("address"),
                jsonObject.getString("telephone"),
                jsonObject.getString("miniPrice"),
                jsonObject.getString("deliveryPrice"),
                jsonObject.getString("description"));
    }

    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    @ResponseBody
    public MenuVO addMenu(@RequestBody JSONObject jsonObject) {
        return shopBLService.addMenu(jsonObject.getString("shopid"), jsonObject.getString("name"));
    }

    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    @ResponseBody
    public String deleteMenu(@RequestBody JSONObject jsonObject) {
        return shopBLService.deleteMenu(jsonObject.getString("id"));
    }

    @RequestMapping(value = "/addFood", method = RequestMethod.POST)
    @ResponseBody
    public FoodVO addFood(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name,
                          @RequestParam("menuid") String menuid, @RequestParam("price") String price,
                          @RequestParam("num") String num) {
        return shopBLService.addFood(menuid, name, num, price, multipartFile);
    }

    @RequestMapping(value = "/modifyFood", method = RequestMethod.POST)
    @ResponseBody
    public String modifyFood(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name,
                             @RequestParam("id") String id, @RequestParam("price") String price,
                             @RequestParam("num") String num) {
        return shopBLService.modifyFood(id, name, num, price, multipartFile);
    }

    @RequestMapping(value = "/deleteFood", method = RequestMethod.POST)
    @ResponseBody
    public String deleteFood(@RequestParam("id") String id) {
        return shopBLService.deleteFood(id);
    }

    @RequestMapping(value = "/addOff", method = RequestMethod.POST)
    @ResponseBody
    public OffVO addOff(@RequestBody JSONObject jsonObject) {
        return shopBLService.addOff(jsonObject.getString("shopid"), jsonObject.getString("price"), jsonObject.getString("off"));
    }

    @RequestMapping(value = "/deleteOff", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOff(@RequestBody JSONObject jsonObject) {
        return shopBLService.deleteOff(jsonObject.getString("shopid"), jsonObject.getString("price"));
    }
}
