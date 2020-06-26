package com.yc.projects.ibike.service;

import com.yc.projects.ibike.bean.User;

import java.util.List;

public interface UserService {

    // 生成一个uuid,以它为键，  sessionkey和openid为值，存到 redis中，且设置超时时间为 30天。
    public String redisSessionKey(String openId,String sessionKey);

    //添加用户到mongo的users集合
    public void addMember(User u);

    // 根据openid到 mongo中的 users集合中查是否有这个人.
    public List<User> selectMember(String openid) ;

    //生成验证码 通过短信发送
    public void genVerifyCode(String nationCode,String phoneNum);

    //发送短信后校验
    public boolean verify(User user);

    //交押金
    public boolean deposit(User user);

    //身份验证
    public boolean identity(User user);

    //充值
    public boolean recharge(double balance ,String phoneNum);
}
