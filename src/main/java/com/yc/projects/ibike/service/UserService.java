package com.yc.projects.ibike.service;

import com.yc.projects.ibike.bean.User;

public interface UserService {


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
