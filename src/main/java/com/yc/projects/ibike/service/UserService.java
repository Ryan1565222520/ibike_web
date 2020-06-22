package com.yc.projects.ibike.service;

import com.yc.projects.ibike.bean.User;

public interface UserService {


    //生成验证码 通过短信发送
    public void genVerifyCode(String nationCode,String phoneNum);


    public boolean verify(User user);

    public boolean deposit(User user);

    public boolean identity(User user);
}
