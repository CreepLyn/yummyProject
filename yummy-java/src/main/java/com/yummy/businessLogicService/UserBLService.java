package com.yummy.businessLogicService;

import com.yummy.vo.RedpacketVO;
import com.yummy.vo.UserVO;

import java.util.List;

public interface UserBLService {

    String login(String email, String password);

    String getCode(String email);

    String register(String email, String password, String type);

    UserVO getInfo(String username);

    List<RedpacketVO> getRedPackge(String username);

    String modify(String username, String email, String telephone, String password);

    Double recharge(String username, String money);

    Double withdraw(String username, String money);

    String logout(String username);

}
