package com.yummy.businessLogic;

import com.yummy.businessLogicService.ShopBLService;
import com.yummy.modal.Food;
import com.yummy.modal.Menu;
import com.yummy.modal.Shop;
import com.yummy.repository.FoodRepository;
import com.yummy.repository.MenuRepository;
import com.yummy.repository.ShopRepository;
import com.yummy.repository.UserRepository;
import com.yummy.util.ConvertUtil;
import com.yummy.util.PictureUtil;
import com.yummy.util.ResultMessage;
import com.yummy.util.StateMessage;
import com.yummy.vo.FoodVO;
import com.yummy.vo.MenuVO;
import com.yummy.vo.OffVO;
import com.yummy.vo.ShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ShopBL implements ShopBLService{

    private static String path = System.getProperty("user.dir") + "/pictures/shops";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private FoodRepository foodRepository;

    /**
     * 得到所有商店列表
     * @return 商店列表
     */
    @Override
    public List<ShopVO> getShopList() {
        List<Shop> shopList = shopRepository.findAll();
        return getShopVoList(shopList);
    }

    /**
     * 根据类型得到商店列表
     * @param type 类型
     * @return 商店类型
     */
    @Override
    public List<ShopVO> getShopListByType(String type) {
        List<Shop> shopList = shopRepository.getAllByType(type);
        return getShopVoList(shopList);
    }

    /**
     * 搜索商店
     * @param text 搜索词
     * @return 商店列表
     */
    @Override
    public List<ShopVO> searchShop(String text) {
        System.out.println(text);
        List<Shop> shopList = shopRepository.findAllByShopnameContaining(text);
        return getShopVoList(shopList);
    }

    /**
     * 根据商家用户得到商铺信息
     * @param username 用户名
     * @return 商家信息
     */
    @Override
    public ShopVO getShopInfo(String username) {
        Shop shop = shopRepository.findByUserid(userRepository.findByUsername(username).getId());
        ShopVO shopVO = ConvertUtil.toShopVO(shop);
        shopVO.setOffList(getOffInfo(shop.getId().toString()));
        shopVO.setMenuList(getMenuInfo(shop.getId().toString()));
        return shopVO;
    }

    /**
     * 得到商店状态
     * @param username 用户名
     * @return 商店状态
     */
    @Override
    public String getShopState(String username) {
        if (shopRepository.findByUserid(userRepository.findByUsername(username).getId()) == null) {
            return ResultMessage.NOT_EXIST;
        }
        return ResultMessage.EXIST;
    }

    /**
     * 得到商铺菜单、商品信息
     * @param shopid 商铺ID
     * @return 详细信息列表
     */
    @Override
    public List<MenuVO> getMenuInfo(String shopid) {
        Shop shop = shopRepository.findShopById(Long.parseLong(shopid));
        List<Menu> menuList = menuRepository.getAllByShopid(shop.getId());
        List<MenuVO> menuVOList = new ArrayList<MenuVO>();
        for (Menu menuItem : menuList){
            MenuVO menuVO = new MenuVO();
            menuVO.setId(menuItem.getId());
            menuVO.setName(menuItem.getName());
            List<Food> foodList = foodRepository.findAllByMenuid(menuItem.getId());
            List<FoodVO> foodVOList = new ArrayList<FoodVO>();
            for (Food foodItem : foodList){
                FoodVO foodVO = ConvertUtil.toFoodVO(foodItem);
                foodVO.setUrl(PictureUtil.imageChangeBase64(path + "/" + shop.getShopname() + "/" + menuItem.getName() + "/" + foodItem.getName() + ".jpg"));
                foodVOList.add(foodVO);
            }
            menuVO.setFoodList(foodVOList);
            menuVOList.add(menuVO);
        }
        return menuVOList;
    }

    /**
     * 得到满减信息
     * @param shopid 商店ID
     * @return 满减列表
     */
    @Override
    public List<OffVO> getOffInfo(String shopid) {
        Map<String, String> off = shopRepository.findShopById(Long.parseLong(shopid)).getOff();
        List<OffVO> offVOList = new ArrayList<OffVO>();
        Iterator iter = off.entrySet().iterator(); // 获得map的Iterator
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            OffVO offVO = new OffVO();
            offVO.setPrice(entry.getKey().toString());
            offVO.setOff(entry.getValue().toString());
            offVOList.add(offVO);
        }
        return offVOList;
    }

    /**
     * 新增商店
     * @param multipartFile 封面图
     * @param shopname 商店名
     * @param type 商店类型
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param address 地址
     * @param telephone 联系方式
     * @param description 详情
     * @param miniPrice 起送价
     * @param deliveryPrice 配送价
     * @param username 用户名
     * @return 操作结果
     */
    @Override
    public String addShop(MultipartFile multipartFile, String shopname, String type, String startTime, String endTime, String address, String telephone, String description, String miniPrice, String deliveryPrice, String username) {
        if (shopRepository.findByShopname(shopname) == null) {
            Shop shop = new Shop();
            shop.setShopname(shopname);
            shop.setAddress(address);
            shop.setTelephone(telephone);
            shop.setType(type);
            shop.setStart_time(startTime);
            shop.setEnd_time(endTime);
            shop.setPacking_price((double) 2);
            shop.setMini_price(Long.parseLong(miniPrice));
            shop.setDelivery_price(Double.parseDouble(deliveryPrice));
            shop.setDescription(description);
            shop.setUserid(userRepository.findByUsername(username).getId());
            shop.setState(StateMessage.CHECK);
            shopRepository.save(shop);
            try {
                File file = new File(path + "/" + shop.getShopname());
                if (!file.exists())
                    file.mkdirs();
                String savePath = path + "/" + shop.getShopname() + "/cover.jpg";
                File testFile = new File(savePath);
                if (testFile.exists()){
                    return ResultMessage.EXIST;
                }
                byte[] bytes = multipartFile.getBytes();
                Path filepath = Paths.get(savePath);
                Files.write(filepath, bytes);
                addMenu(shopRepository.findByShopname(shopname).getId().toString(), "热销");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.EXIST;
        }
    }

    /**
     * 修改商铺信息
     * @param shopname 商铺名
     * @param type 商铺类型
     * @param startTime 营业时间
     * @param endTime 关店时间
     * @param address 详细地址
     * @param telephone 联系方式
     * @param miniPrice 起送价
     * @param deliveryPrice 配送费
     * @param description 店家简介
     * @return 修改结果
     */
    @Override
    public String modifyShop(String shopname, String type, String startTime, String endTime, String address, String telephone, String miniPrice, String deliveryPrice, String description) {
        Shop shop = shopRepository.findByShopname(shopname);
        shop.setAddress(address);
        shop.setTelephone(telephone);
        shop.setType(type);
        shop.setStart_time(startTime);
        shop.setEnd_time(endTime);
        shop.setMini_price(Long.parseLong(miniPrice));
        shop.setDelivery_price(Double.parseDouble(deliveryPrice));
        shop.setDescription(description);
        shop.setState(StateMessage.CHECK);
        shopRepository.save(shop);
        return ResultMessage.SUCCESS;
    }

    /**
     * 增加菜单
     * @param shopid 商品ID
     * @param name 菜单名
     * @return 操作结果
     */
    @Override
    public MenuVO addMenu(String shopid, String name) {
        Shop shop = shopRepository.findShopById(Long.parseLong(shopid));
        Menu menu = new Menu();
        menu.setShopid(Long.parseLong(shopid));
        menu.setName(name);
        menuRepository.save(menu);
        File file = new File(path + "/" + shop.getShopname() + "/" + name);
        file.mkdirs();
        return ConvertUtil.toMenuVO(menuRepository.getByShopidAndAndName(shop.getId(), name));
    }

    /**
     * 删除菜单（包括菜单下的食品）
     * @param id 菜单ID
     * @return 操作结果
     */
    @Override
    public String deleteMenu(String id) {
        Menu menu = menuRepository.getMenuById(Long.parseLong(id));
        Shop shop = shopRepository.findShopById(menu.getShopid());
        String savePath = path + "/" + shop.getShopname() + "/" + menu.getName();
        if (deleteDirectory(savePath)) {
            menuRepository.deleteById(Long.parseLong(id));
            foodRepository.deleteAllByMenuid(Long.parseLong(id));
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILURE;
        }
    }

    /**
     * 增加食物
     * @param menuid 菜单ID
     * @param name 食物名
     * @param num 食物数量
     * @param price 食物单价
     * @param multipartFile 封面图
     * @return 操作结果
     */
    @Override
    public FoodVO addFood(String menuid, String name, String num, String price, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            Menu menu = menuRepository.getMenuById(Long.parseLong(menuid));
            Shop shop = shopRepository.findShopById(menu.getShopid());
            try {
                File file = new File(path + "/" + shop.getShopname() + "/" + menu.getName());
                if (!file.exists())
                    file.mkdirs();
                String savePath = path + "/" + shop.getShopname() + "/" + menu.getName() + "/" + name;
                File testFile = new File(savePath);
                if (testFile.exists()){
                    return null;
                }else {
                    Food food = new Food();
                    food.setName(name);
                    food.setNum(Integer.parseInt(num));
                    food.setMenuid(menu.getId());
                    food.setPrice(Double.parseDouble(price));
                    foodRepository.save(food);
                    savePath = savePath + ".jpg";
                }
                byte[] bytes = multipartFile.getBytes();
                Path filepath = Paths.get(savePath);
                Files.write(filepath, bytes);
                FoodVO foodVO = ConvertUtil.toFoodVO(foodRepository.getFoodByMenuidAndName(menu.getId(), name));
                foodVO.setUrl(PictureUtil.imageChangeBase64(path + "/" + shop.getShopname() + "/" + menu.getName() + "/" + name + ".jpg"));
                return foodVO;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 修改食物信息
     * @param id 食物ID
     * @param num 食物数量
     * @param price 食物单价
     * @return 操作结果
     */
    @Override
    public String modifyFood(String id, String name, String num, String price, MultipartFile multipartFile) {
        Food food = foodRepository.getFoodById(Long.parseLong(id));
        Menu menu = menuRepository.getMenuById(food.getMenuid());
        Shop shop = shopRepository.findShopById(menu.getShopid());
        try {
            File file = new File(path + "/" + shop.getShopname() + "/" + menu.getName());
            if (!file.exists())
                file.mkdirs();
            String savePath = path + "/" + shop.getShopname() + "/" + menu.getName() + "/" + food.getName() + ".jpg";
            File testFile = new File(savePath);
            if (testFile.exists()){
                testFile.delete();
            } else {
                savePath = path + "/" + shop.getShopname() + "/" + menu.getName() + "/" + name + ".jpg";
            }
            byte[] bytes = multipartFile.getBytes();
            Path filepath = Paths.get(savePath);
            Files.write(filepath, bytes);
            food.setName(name);
            food.setNum(Integer.parseInt(num));
            food.setPrice(Double.parseDouble(price));
            foodRepository.save(food);
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.FAILURE;
    }

    /**
     * 删除食物
     * @param id 食物ID
     * @return 操作结果
     */
    @Override
    public String deleteFood(String id) {
        Food food = foodRepository.getFoodById(Long.parseLong(id));
        Menu menu = menuRepository.getMenuById(food.getMenuid());
        Shop shop = shopRepository.findShopById(menu.getShopid());
        String savePath = path + "/" + shop.getShopname() + "/" + menu.getName() + "/" + food.getName() + ".png";
        if (deleteFile(savePath)) {
            foodRepository.delete(food);
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILURE;
        }
    }

    /**
     * 新增满减信息
     * @param shopid 商店ID
     * @param price 满额
     * @param off 优惠
     * @return 结果
     */
    @Override
    public OffVO addOff(String shopid, String price, String off) {
        Shop shop = shopRepository.findShopById(Long.parseLong(shopid));
        Map<String, String> offList = shop.getOff();
        offList.put(price, off);
        shop.setOff(offList);
        shopRepository.save(shop);
        return new OffVO(off, price);
    }

    /**
     * 删除满减
     * @param shopid 商店ID
     * @param price 满额
     * @return 结果
     */
    @Override
    public String deleteOff(String shopid, String price) {
        Shop shop = shopRepository.findShopById(Long.parseLong(shopid));
        Map<String, String> offList = shop.getOff();
        offList.remove(price);
        shop.setOff(offList);
        shopRepository.save(shop);
        return ResultMessage.SUCCESS;
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    private boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到VO列表
     * @param shopList 商店数据库列表
     * @return 得到前后端交互的商店列表
     */
    private List<ShopVO> getShopVoList(List<Shop> shopList) {
        List<ShopVO> shopVOList = new ArrayList<ShopVO>();
        for (Shop shopItem: shopList) {
            if (shopItem.getState().equals(StateMessage.PASS)) {
                shopVOList.add(ConvertUtil.toShopVO(shopItem));
            }
        }
        return shopVOList;
    }

}
