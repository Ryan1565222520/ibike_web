package com.yc.projects.ibike.web.controller;

import com.yc.projects.ibike.bean.PayModel;
import com.yc.projects.ibike.bean.User;
import com.yc.projects.ibike.service.BikeService;
import com.yc.projects.ibike.service.PayService;
import com.yc.projects.ibike.service.UserService;
import com.yc.projects.ibike.web.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Ryan_Tang
 * @Date 2020/6/26 15:06
 */
@Controller
public class PayController {
    @Autowired
    private UserService userService;
    @Autowired
    private BikeService bikeService;
    @Autowired
    private PayService payService;


    @PostMapping("/payMoney")
    public @ResponseBody JsonModel payMoney(JsonModel jsonModel, PayModel pm) {
        try {
            payService.pay(  pm );
            jsonModel.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonModel.setCode(0);
            jsonModel.setMsg(  e.getMessage());
        }
        return jsonModel;
    }


    @RequestMapping("/recharge")
    public @ResponseBody
    JsonModel recharge(JsonModel jsonModel, User user){
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
