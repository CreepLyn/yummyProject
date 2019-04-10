package com.yummy.businessLogic;

import com.yummy.businessLogicService.UserBLService;
import com.yummy.modal.Redpacket;
import com.yummy.modal.User;
import com.yummy.repository.RedpacketRepository;
import com.yummy.repository.UserRepository;
import com.yummy.util.ConvertUtil;
import com.yummy.util.RedPacketUtil;
import com.yummy.util.ResultMessage;
import com.yummy.util.TypeMessage;
import com.yummy.vo.RedpacketVO;
import com.yummy.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserBL implements UserBLService {

    private String sender = "creeplyn98zy@163.com";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedpacketRepository redpacketRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    private RedPacketUtil redPacketUtil = new RedPacketUtil();

    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @return 成功则返回职业，否则返回结果
     */
    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null){
            return ResultMessage.NOT_EXIST;
        } else {
            if (user.getPassword().equals(password)) {
                return user.getType() + "," + user.getUsername();
            } else {
                return ResultMessage.FAILURE;
            }
        }
    }

    /**
     * 发送验证码给指定邮箱
     * @param email 邮箱
     * @return 验证码
     */
    @Override
    public String getCode(String email) {
        MimeMessage mimeMailMessage = null;
        String code = String.format("%04d",new Random().nextInt(9999));
        System.out.println(code);
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(sender);
            mimeMessageHelper.setCc(email);
            mimeMessageHelper.setSubject("标题：Yummy验证码");
            String sb = "<h1>欢迎您加入YUMMY！</h1>" +
                    "<span style='font-size: 20px'>您的验证码是：</span>" +
                    "<span style='font-size: 26px; font-weight: 600; color: #d64b29'>" + code + "</span>";

            mimeMessageHelper.setText(sb, true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            System.out.println("邮件发送失败" + e.getMessage());
        }
        return code;
    }

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param type 用户类型
     * @return 注册结果
     */
    @Override
    public String register(String email, String password, String type) {
        if (userRepository.findByEmail(email) == null){
            User user = new User();
            user.setBalance((double) 0);
            user.setPassword(password);
            user.setTelephone("");
            user.setEmail(email);
            user.setType(type);
            user.setUsername(email);
            user.setLevel("1");
            if (type.equals(TypeMessage.MERCHANT)) {
                String code = String.format("%04d",new Random().nextInt(9999999));
                user.setUsername(code);
            }
            userRepository.save(user);
            return ResultMessage.SUCCESS;
        } else {
            return ResultMessage.EXIST;
        }
    }

    /**
     * 得到用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserVO getInfo(String username) {
        return ConvertUtil.toUserVO(userRepository.findByUsername(username));
    }

    /**
     * 得到用户红包列表
     * @param username 用户名
     * @return 红包列表
     */
    @Override
    public List<RedpacketVO> getRedPackge(String username) {
        User user = userRepository.findByUsername(username);
        List<Redpacket> redpacketList = redpacketRepository.findAllByUserid(user.getId());
        List<RedpacketVO> redpacketVOList = new ArrayList<RedpacketVO>();
        if (redpacketList.isEmpty()) {
            //关于红包初始问题
            int level = Integer.parseInt(user.getLevel());
            redpacketList = redPacketUtil.getElement(level);
            if (level == 0)
                return redpacketVOList;
        }
        for (Redpacket redpacket : redpacketList) {
            redpacket.setUserid(user.getId());
            redpacketRepository.save(redpacket);
            redpacketVOList.add(ConvertUtil.toRedpacketVO(redpacket));
        }
        return redpacketVOList;
    }

    /**
     * 修改个人信息
     * @param username 昵称
     * @param email 邮箱
     * @param telephone 联系方式
     * @return 修改结果
     */
    @Override
    public String modify(String username, String email, String telephone, String password) {
        User oldUser = userRepository.findByUsername(username);
        oldUser.setEmail(email);
        oldUser.setTelephone(telephone);
        if (!password.equals(TypeMessage.NOPASSWORD)) {
            oldUser.setPassword(password);
        }
        userRepository.save(oldUser);
        return ResultMessage.SUCCESS;
    }

    /**
     * 用户充值
     * @param username 用户名
     * @param money 充值金额
     * @return 用户余额
     */
    @Override
    public Double recharge(String username, String money) {
        User user = userRepository.findByUsername(username);
        Double balance = user.getBalance() + Double.parseDouble(money);
        user.setBalance(balance);
        userRepository.save(user);
        return balance;
    }

    /**
     * 商家提现
     * @param username 用户名
     * @param money 提现金额
     * @return 用户余额
     */
    @Override
    public Double withdraw(String username, String money) {
        User user = userRepository.findByUsername(username);
        Double balance = user.getBalance() - Double.parseDouble(money);
        user.setBalance(balance);
        userRepository.save(user);
        return balance;
    }

    /**
     * 用户注销
     * @param username 用户名
     * @return 结果
     */
    @Override
    public String logout(String username) {
        User user = userRepository.findByUsername(username);
        user.setType(TypeMessage.LOGOUT);
        userRepository.save(user);
        return ResultMessage.SUCCESS;
    }

}
