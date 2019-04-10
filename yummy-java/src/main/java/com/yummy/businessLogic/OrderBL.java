package com.yummy.businessLogic;

import com.yummy.businessLogicService.OrderBLService;
import com.yummy.modal.*;
import com.yummy.repository.*;
import com.yummy.util.ConvertUtil;
import com.yummy.util.NumberMessage;
import com.yummy.util.ResultMessage;
import com.yummy.util.StateMessage;
import com.yummy.vo.FoodVO;
import com.yummy.vo.OrderVO;
import com.yummy.vo.StateVO;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderBL implements OrderBLService {

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat codeSdf=new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat simpleSdf=new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderinfoRepository orderinfoRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private RedpacketRepository redpacketRepository;

    /**
     * 提交订单信息
     * @param username 用户名
     * @param shopid 商店ID
     * @param total 总价
     * @param addressid 地址ID
     * @param off 满减
     * @return 操作结果
     */
    @Override
    public String payOrder(String username, String shopid, String total, String addressid, String off, String state) {
        User user = userRepository.findByUsername(username);
        String code = getOrderCode(user.getId());
        Orders orders = new Orders();
        orders.setCode(code);
        orders.setShopid(Long.parseLong(shopid));
        orders.setUserid(user.getId());
        orders.setOff(Integer.parseInt(off));
        Date date = new Date();
        orders.setDate(sdf.format(date));
        orders.setPrice(Double.parseDouble(total));
        orders.setState(state);
        orders.setExpressmanid(Long.parseLong("7"));
        orders.setAddressid(Long.parseLong(addressid));
        System.out.println(sdf.format(new Date()) + "用户下单");
        if (state.equals(StateMessage.PROCESS)) {
            if (user.getBalance() > Double.parseDouble(total)) {
                double rest = user.getBalance() - Double.parseDouble(total);
                user.setBalance(rest);
                userRepository.save(user);
                orderRepository.save(orders);
                orderThread(orderRepository.getOrdersByCode(code).getId());
                return code;
            } else {
                return ResultMessage.FAILURE;
            }
        } else {
            orderRepository.save(orders);
            Timer timer = new Timer();
            timer.schedule(getPayTimerTask(orderRepository.getOrdersByCode(code).getId()), NumberMessage.payTimer);
            return code;
        }
    }

    /**
     * 保存订单信息，修改食物数量
     * @param code 订单号
     * @param redid 红包ID
     * @param foodList 食物列表
     * @return 操作结果
     */
    @Override
    public String saveFoodList(String code,String redid,  JSONArray foodList) {
        Orders orders = orderRepository.getOrdersByCode(code);
        List cartList = (List) JSONArray.toList(foodList, FoodVO.class);
        boolean isValid = true;
        for (Object aCartList : cartList) {
            FoodVO food = (FoodVO) aCartList;
            Orderinfo orderinfo = new Orderinfo();
            orderinfo.setFoodid(food.getId());
            orderinfo.setOrderid(orders.getId());
            orderinfo.setNum(food.getMenge());
            orderinfoRepository.save(orderinfo);
            Food oldFood = foodRepository.getFoodById(food.getId());
            if (oldFood.getNum() > food.getMenge()){
                int rest = oldFood.getNum() - food.getMenge();
                oldFood.setNum(rest);
                foodRepository.save(oldFood);
            } else {
                isValid = false;
            }
        }
        Date nowDate = new Date();
        String nowTime = sdf.format(nowDate).split(" ")[1];
        Track track = new Track();
        track.setOrderid(orders.getId());
        track.setPlace_time(orders.getDate().split(" ")[1]);
        if (isValid) {
            if (orders.getState().equals(StateMessage.PROCESS)){
                track.setPay_time(nowTime);
            }
            trackRepository.save(track);
            //商家获得利润，应该是确认收到的时候再结算，暂时写在这里
//            User merchant = userRepository.findUserById(shopRepository.findShopById(orders.getShopid()).getUserid());
//            Double balance = merchant.getBalance();
//            if (orders.getPrice() > NumberMessage.priceLimit) {
//                 balance += orders.getPrice() * (1 - NumberMessage.profitPercent);
//            } else {
//                balance += NumberMessage.profitLimit;
//            }
//            merchant.setBalance(balance);
//            userRepository.save(merchant);
            if (redid.equals("-1")) {
                orders.setRedoff(0);
            } else {
                Redpacket redpacket = redpacketRepository.findRedpacketById(Long.parseLong(redid));
                orders.setRedoff(redpacket.getBonus());
                redpacketRepository.delete(redpacket);//默认只要使用了红包就用户的该红包，不考虑取消订单的情况
            }
            return ResultMessage.SUCCESS;
        } else {
            orders.setState(StateMessage.FAIL);
            orderRepository.save(orders);
            //之后的所有时间都是当前时间，待商定！！
            track.setPay_time(nowTime);
            track.setReceive_time(nowTime);
            track.setDeliver_time(nowTime);
            track.setArrive_time(nowTime);
            trackRepository.save(track);
            User user = userRepository.findUserById(orders.getId());
            double rest = user.getBalance() + orders.getPrice();
            user.setBalance(rest);
            userRepository.save(user);
            for (Object aCartList : cartList) {
                FoodVO food = (FoodVO) aCartList;
                Food oldFood = foodRepository.getFoodById(food.getId());
                int origin = oldFood.getNum() + food.getMenge();
                oldFood.setNum(origin);
                foodRepository.save(oldFood);
            }
            return ResultMessage.FAILURE;
        }
    }

    /**
     * 用户支付订单
     * @param id 订单ID
     * @return 操作结果
     */
    @Override
    public String doPayOrder(String id) {
        Orders orders = orderRepository.getOrdersById(Long.parseLong(id));
        User user = userRepository.findUserById(orders.getUserid());
        Double total = orders.getPrice();
        if (user.getBalance() > total) {
            double rest = user.getBalance() - total;
            user.setBalance(rest);
            userRepository.save(user);
            orders.setState(StateMessage.PROCESS);
            orderRepository.save(orders);
            Date nowDate = new Date();
            String nowTime = sdf.format(nowDate).split(" ")[1];
            Track track = trackRepository.getTrackByOrderid(orders.getId());
            track.setPay_time(nowTime);
            trackRepository.save(track);
            orderThread(Long.parseLong(id));
            System.out.println(sdf.format(new Date()) + "用户支付");
            //判断地点是否可行
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.FAILURE;
        }
    }

    /**
     * 得到用户的全部订单
     * @param username 用户民
     * @return 订单列表
     */
    @Override
    public List<OrderVO> getAllOrderByUsername(String username) {
        Long userid = userRepository.findByUsername(username).getId();
        List<Orders> ordersList = orderRepository.getAllByUserid(userid);
        return convertToOrderVOList(ordersList);
    }

    /**
     * 得到商店订单列表
     * @param username 商店用户名
     * @return 订单列表
     */
    @Override
    public List<OrderVO> getAllOrderByMerchant(String username) {
        User user = userRepository.findByUsername(username);
        Shop shop = shopRepository.findByUserid(user.getId());
        List<Orders> ordersList = orderRepository.getAllByShopid(shop.getId());
        return convertToOrderVOList(ordersList);
    }

    /**
     * 用户取消订单
     * @param id 订单号
     * @return 操作结果
     */
    @Override
    public String cancelOrder(String id) {
        Orders orders = orderRepository.getOrdersById(Long.parseLong(id));
        if (orders.getState().equals(StateMessage.ARRIVAL)) {
            //已送达的单子将不可取消
            return ResultMessage.FAILURE;
        } else {
            //送出5分钟后也不可取消
            Track track = trackRepository.getTrackByOrderid(orders.getId());
            String deliverTime = track.getDeliver_time();
            if (deliverTime != null) {
                Date nowDate = new Date();
                String deliverString = simpleSdf.format(nowDate) + " " + deliverTime;
                try {
                    Date deliverDate = sdf.parse(deliverString);
                    if ((nowDate.getTime() - deliverDate.getTime()) / 1000 > NumberMessage.cancelTimeLimit) {
                        System.out.println(sdf.format(new Date()) + "送出超过5分钟不可取消");
                        return ResultMessage.FAILURE;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //其余情况均可以取消订单
            resetFoodNum(orders.getId());
            if (orders.getState().equals(StateMessage.PROCESS)) {
                //如果是已经支付的订单，那么用户需要支付退订费用
                double price = 1.0;
                if (orders.getPrice() > NumberMessage.priceLimit) {
                    price = orders.getPrice() * NumberMessage.cancelPercent;
                }
                User user = userRepository.findUserById(orders.getUserid());
                User merchant = userRepository.findUserById(shopRepository.findShopById(orders.getShopid()).getUserid());
                double balance = user.getBalance() - price;
                double profit = merchant.getBalance() + price;
                user.setBalance(balance);
                merchant.setBalance(price);
                userRepository.save(user);
                userRepository.save(merchant);
            }
            orders.setState(StateMessage.CANCEL);
            orderRepository.save(orders);
            Date nowDate = new Date();
            String nowTime = sdf.format(nowDate).split(" ")[1];
            track.setCancel_time(nowTime);
            trackRepository.save(track);
            System.out.println(sdf.format(new Date()) + "用户取消订单");
            return ResultMessage.SUCCESS;
        }
    }

    /**
     * 用户确认送达
     * @param id 订单ID
     * @return 操作结果
     */
    @Override
    public String checkOrder(String id) {
        Orders orders = orderRepository.getOrdersById(Long.parseLong(id));
        orders.setState(StateMessage.FINISH);
        orderRepository.save(orders);
        User merchant = userRepository.findUserById(shopRepository.findShopById(orders.getShopid()).getUserid());
        Double balance = merchant.getBalance();
        if (orders.getPrice() > NumberMessage.priceLimit) {
            balance += orders.getPrice() * (1 - NumberMessage.profitPercent);
        } else {
            balance += NumberMessage.profitLimit;
        }
        merchant.setBalance(balance);
        userRepository.save(merchant);
        Track track = trackRepository.getTrackByOrderid(Long.parseLong(id));
        Date nowDate = new Date();
        String nowTime = sdf.format(nowDate).split(" ")[1];
        track.setArrive_time(nowTime);
        trackRepository.save(track);
        System.out.println(sdf.format(new Date()) + "用户确认送达");
        return ResultMessage.SUCCESS;
    }

    /**
     * 商家接单
     * @param id 订单ID
     * @return 操作结果
     */
    @Override
    public String receiveOrder(String id) {
        Orders orders = orderRepository.getOrdersById(Long.parseLong(id));
        orders.setState(StateMessage.RECEIVE);
        orderRepository.save(orders);
        Track track = trackRepository.getTrackByOrderid(Long.parseLong(id));
        Date nowDate = new Date();
        String nowTime = sdf.format(nowDate).split(" ")[1];
        track.setReceive_time(nowTime);
        trackRepository.save(track);
        System.out.println(sdf.format(new Date()) + "商家接单");
        return ResultMessage.SUCCESS;
    }

    /**
     * 商家退单
     * @param id 订单ID
     * @return 操作结果
     */
    @Override
    public String quitOrder(String id) {
        Orders orders = orderRepository.getOrdersById(Long.parseLong(id));
        resetFoodNum(orders.getId());
        if (!orders.getState().equals(StateMessage.AWAIT)) {
            //如果用户已经支付，那么商家需要支付一定金额
            double price = 1.0;
            if (orders.getPrice() > NumberMessage.priceLimit) {
                price = orders.getPrice() * NumberMessage.cancelPercent;
            }
            User user = userRepository.findUserById(orders.getUserid());
            User merchant = userRepository.findUserById(shopRepository.findShopById(orders.getShopid()).getUserid());
            double balance = user.getBalance() + price;
            double profit = merchant.getBalance() - price;
            user.setBalance(balance);
            merchant.setBalance(price);
            userRepository.save(user);
            userRepository.save(merchant);
        }
        orders.setState(StateMessage.CANCEL);
        orderRepository.save(orders);
        Date nowDate = new Date();
        String nowTime = sdf.format(nowDate).split(" ")[1];
        Track track = trackRepository.getTrackByOrderid(orders.getId());
        track.setCancel_time(nowTime);
        trackRepository.save(track);
        System.out.println(sdf.format(new Date()) + "店家退单");
        return ResultMessage.SUCCESS;
    }

    /**
     * 生成订单号
     * @param userid 用户ID
     * @return 订单号
     */
    private String getOrderCode(Long userid){
        String date = codeSdf.format(new java.util.Date());
        int random = (int) Math.random() * 10;
        if (random == 0) {
            random = 1;
        }
        return random + date + userid.toString();
    }

    /**
     * 转换为订单信息列表
     * @param ordersList 数据库订单数据列表
     * @return 订单信息列表
     */
    private List<OrderVO> convertToOrderVOList(List<Orders> ordersList) {
        List<OrderVO> orderVOList = new ArrayList<OrderVO>();
        for (Orders ordersItem : ordersList) {
            OrderVO orderVO = ConvertUtil.toOrderVO(ordersItem);
            orderVO.setUsername(userRepository.findUserById(ordersItem.getUserid()).getUsername());
            orderVO.setShop(ConvertUtil.toShopVO(shopRepository.findShopById(ordersItem.getShopid())));
//            orderVO.setTrack(ConvertUtil.toTrackVO(trackRepository.getTrackByOrderid(ordersItem.getId())));
            orderVO.setAddress(ConvertUtil.toAddressVO(addressRepository.getAddressById(ordersItem.getAddressid())));

            List<Orderinfo> orderinfoList = orderinfoRepository.findAllByOrderid(ordersItem.getId());
            List<FoodVO> foodVOList = new ArrayList<FoodVO>();
            for (Orderinfo orderinfo: orderinfoList) {
                FoodVO foodVO = ConvertUtil.toFoodVO(foodRepository.getFoodById(orderinfo.getFoodid()));
                foodVO.setMenge(orderinfo.getNum());
                foodVOList.add(foodVO);
            }
            orderVO.setFoodList(foodVOList);

            List<StateVO> stateList = new ArrayList<StateVO>();
            Track track = trackRepository.getTrackByOrderid(ordersItem.getId());
            stateList.add(new StateVO(StateMessage.OrderPlace, track.getPlace_time()));
            if (track.getPay_time() != null) {
                stateList.add(new StateVO(StateMessage.OrderPay, track.getPay_time()));
            } else {
                stateList.add(new StateVO(StateMessage.OrderPay, "无"));
            }
            if (track.getReceive_time() != null) {
                stateList.add(new StateVO(StateMessage.OrderReceive, track.getReceive_time()));
            } else {
                stateList.add(new StateVO(StateMessage.OrderReceive, "无"));
            }
            if (track.getDeliver_time() != null) {
                stateList.add(new StateVO(StateMessage.OrderDeliver, track.getDeliver_time()));
            } else {
                stateList.add(new StateVO(StateMessage.OrderDeliver, "无"));
            }
            if (track.getArrive_time() != null) {
                stateList.add(new StateVO(StateMessage.OrderFinish, track.getArrive_time()));
            } else {
                stateList.add(new StateVO(StateMessage.OrderFinish, "无"));
            }
            if (track.getCancel_time() != null) {
                stateList.remove(4);
                stateList.add(new StateVO(StateMessage.OrderCancel, track.getCancel_time()));
            } else {
                stateList.add(new StateVO(StateMessage.OrderCancel, "无"));
            }
            orderVO.setStateList(stateList);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    /**
     * 恢复食品数量
     * @param id 订单ID
     */
    private void resetFoodNum(Long id) {
        List<Orderinfo> orderinfoList = orderinfoRepository.findAllByOrderid(id);
        for (Orderinfo orderinfo: orderinfoList) {
            Food food = foodRepository.getFoodById(orderinfo.getFoodid());
            int num = food.getNum() + orderinfo.getNum();
            food.setNum(num);
            foodRepository.save(food);
        }
    }

    /**
     * 订单状态追踪线程
     * @param id 订单ID
     */
    private void orderThread(Long id) {
        Timer timer = new Timer();
        timer.schedule(getReceiveTimerTask(id), NumberMessage.receiveTimer);
        timer.schedule(getDeliverTimerTask(id), NumberMessage.receiveTimer + NumberMessage.deliverTimer);
        timer.schedule(getArriveTimerTask(id), NumberMessage.receiveTimer + NumberMessage.deliverTimer + NumberMessage.arriveTimer);
        timer.schedule(getCheckTimerTask(id), NumberMessage.receiveTimer + NumberMessage.deliverTimer + NumberMessage.arriveTimer + NumberMessage.checkTimer);
    }

    /**
     * 支付任务
     * @param id 订单ID
     * @return 操作结果
     */
    private TimerTask getPayTimerTask(Long id){
        return new TimerTask() {
            @Override
            public void run() {
                Orders orders = orderRepository.getOrdersById(id);
                if (orders.getState().equals(StateMessage.AWAIT)) {
                    System.out.println(sdf.format(new Date()) + "订单已经超过时间仍未支付！");
                    cancelOrder(orders.getId().toString());
                }
            }
        };
    }

    /**
     * 接单任务
     * @param id 订单ID
     * @return 操作结果
     */
    private TimerTask getReceiveTimerTask(Long id){
        return new TimerTask() {
            @Override
            public void run() {
                Orders orders = orderRepository.getOrdersById(id);
                if (orders.getState().equals(StateMessage.PROCESS)) {
                    System.out.println(sdf.format(new Date()) + "商家未接单系统自动接单");
                    orders.setState(StateMessage.RECEIVE);
                    orderRepository.save(orders);
                    Track track = trackRepository.getTrackByOrderid(orders.getId());
                    Date nowDate = new Date();
                    String nowTime = sdf.format(nowDate).split(" ")[1];
                    track.setReceive_time(nowTime);
                    trackRepository.save(track);
                }
            }
        };
    }

    /**
     * 配送任务
     * @param id 订单ID
     * @return 操作结果
     */
    private TimerTask getDeliverTimerTask(Long id){
        return new TimerTask() {
            @Override
            public void run() {
                Orders orders = orderRepository.getOrdersById(id);
                System.out.println(sdf.format(new Date()) + "配送员开始配送");
                Track track = trackRepository.getTrackByOrderid(orders.getId());
                Date nowDate = new Date();
                String nowTime = sdf.format(nowDate).split(" ")[1];
                track.setDeliver_time(nowTime);
                trackRepository.save(track);
            }
        };
    }

    /**
     * 送达任务
     * @param id 订单ID
     * @return 操作结果
     */
    private TimerTask getArriveTimerTask(Long id){
        return new TimerTask() {
            @Override
            public void run() {
                Orders orders = orderRepository.getOrdersById(id);
                System.out.println(sdf.format(new Date()) + "配送员已经送达");
                orders.setState(StateMessage.ARRIVAL);
                orderRepository.save(orders);
                Track track = trackRepository.getTrackByOrderid(orders.getId());
                Date nowDate = new Date();
                String nowTime = sdf.format(nowDate).split(" ")[1];
                track.setArrive_time(nowTime);
                trackRepository.save(track);
            }
        };
    }

    /**
     * 完成任务
     * @param id 订单ID
     * @return 操作结果
     */
    private TimerTask getCheckTimerTask(Long id){
        return new TimerTask() {
            @Override
            public void run() {
                Orders orders = orderRepository.getOrdersById(id);
                if (orders.getState().equals(StateMessage.ARRIVAL)) {
                    System.out.println(sdf.format(new Date()) + "用户未确认送达，系统自动确认");
                    checkOrder(orders.getId().toString());
                }
            }
        };
    }
}
