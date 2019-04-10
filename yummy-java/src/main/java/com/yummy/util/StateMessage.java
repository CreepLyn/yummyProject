package com.yummy.util;

public class StateMessage {

    public static final String PROCESS = "进行中";
    public static final String RECEIVE = "已接单";
    public static final String FINISH = "已完成";
    public static final String ARRIVAL = "已送达";
    public static final String FAIL = "未完成";
    public static final String AWAIT = "待支付";
    public static final String CANCEL = "已取消";
    public static final String CHECK = "待审核";
    public static final String PASS = "已通过";
    public static final String REJECT = "已驳回";

    public static final String OrderPlace = "订单提交成功，等待付款";
    public static final String OrderPay = "支付成功，等待商家接单";
    public static final String OrderReceive = "商家接单，美食制作中";
    public static final String OrderDeliver = "派送员已到点，正在配送中";
    public static final String OrderFinish = "订单已完成";
    public static final String OrderCancel = "订单已取消";

}
