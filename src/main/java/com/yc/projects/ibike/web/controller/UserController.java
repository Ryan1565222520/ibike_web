package com.yc.projects.ibike.web.controller;

import com.yc.projects.ibike.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.ibike.service.UserService;
import com.yc.projects.ibike.web.model.JsonModel;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/genCode")
    public @ResponseBody JsonModel genSMSCode(JsonModel jm, String nationCode, String phoneNum) {
        String msg = "true";
        try {
            // 生成4位随机数 -> 调用短信接口发送验证码 -> 将手机号对应的验证码保存到redis中，并且设置这个key的有效时长
            userService.genVerifyCode(nationCode, phoneNum);
            jm.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            jm.setCode(0);
            jm.setMsg(e.getMessage());
        }
        return jm;
    }

    @RequestMapping("/verify")
    public @ResponseBody  JsonModel getSmsCode(JsonModel jsonModel, User user){
        boolean flag=false;
        try {
            flag=userService.verify(user);
            if(flag){
                jsonModel.setCode(1);
            }else{
                jsonModel.setCode(0);
                jsonModel.setMsg("校验码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonModel.setCode(0);
            jsonModel.setMsg(e.getMessage());
        }
        return jsonModel;
    }

    //押金
    @RequestMapping("/deposit")
    public @ResponseBody  JsonModel deposit(JsonModel jsonModel, User user){
        try {
            boolean flag=userService.deposit(user);
            if(flag){
                jsonModel.setCode(1);
            }else{
                jsonModel.setCode(0);
                jsonModel.setMsg("校验码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonModel.setCode(0);
            jsonModel.setMsg(e.getMessage());
        }
        return jsonModel;
    }

    //实名制
    @RequestMapping("/identity")
    public @ResponseBody  JsonModel identity(JsonModel jsonModel, User user){
        try {
            boolean flag=userService.identity(user);
            if(flag){
                jsonModel.setCode(1);
            }else{
                jsonModel.setCode(0);
                jsonModel.setMsg("身份信息不符，请重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonModel.setCode(0);
            jsonModel.setMsg(e.getMessage());
        }
        return jsonModel;
    }


    @RequestMapping("/recharge")
    public @ResponseBody  JsonModel recharge(JsonModel jsonModel, User user){
        try {
            boolean flag=userService.recharge(user.getBalance(),user.getPhoneNum());
            if(flag){
                jsonModel.setCode(1);
            }else{
                jsonModel.setCode(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonModel.setCode(0);
            jsonModel.setMsg(e.getMessage());
        }
        return jsonModel;
    }
}