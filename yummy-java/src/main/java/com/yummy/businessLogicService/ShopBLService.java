package com.yummy.businessLogicService;

import com.yummy.vo.FoodVO;
import com.yummy.vo.MenuVO;
import com.yummy.vo.OffVO;
import com.yummy.vo.ShopVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopBLService {

    List<ShopVO> getShopList();

    List<ShopVO> getShopListByType(String type);

    List<ShopVO> searchShop(String text);

    ShopVO getShopInfo(String username);

    String getShopState(String username);

    List<MenuVO> getMenuInfo(String shopid);

    List<OffVO> getOffInfo(String shopid);

    String addShop(MultipartFile multipartFile, String shopname, String type, String startTime, String endTime, String address, String telephone, String description, String miniPrice, String deliveryPrice, String username);

    String modifyShop(String shopname, String type, String startTime, String endTime, String address, String telephone, String miniPrice, String deliveryPrice, String description);

    MenuVO addMenu(String shopid, String name);

    String deleteMenu(String id);

    FoodVO addFood(String menuid, String name, String num, String price, MultipartFile multipartFile);

    String modifyFood(String id, String name, String num, String price, MultipartFile multipartFile);

    String deleteFood(String id);

    OffVO addOff(String shopid, String price, String off);

    String deleteOff(String shopid, String price);

}
