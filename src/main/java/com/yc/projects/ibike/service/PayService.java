package com.yc.projects.ibike.service;

import com.yc.projects.ibike.bean.PayModel;

/**
 * @Author Ryan_Tang
 * @Date 2020/6/26 15:13
 */
public interface PayService {
    /**
     * 1.计算金额
     * 2.将数据保存到mongo的payLog中   ()
     * 3.修改单车的经纬度和状态    状态为1
     * @param payModel
     */
    public void pay(PayModel payModel);

}
