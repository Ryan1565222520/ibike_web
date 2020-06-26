package com.yc.projects.ibike.service;

import com.yc.projects.ibike.bean.Bike;

import java.util.List;
public interface BikeService {

    /**
     * 开锁     1，bid必须  2.根据bid查车  3.车的状态
     * @param bike
     */
    public void open(Bike bike );

    /**
     * 根据bid查车
     * @param bid
     * @return
     */
    public Bike findByBid(String bid);

    /**
     * 新车上架   必须生成bid 且根据bid生成二维码
     * @param bike
     * @return
     */
    public Bike addNewBike(Bike bike);

    public List<Bike> findNearAll(Bike bike);

    //报修
    public void reportMantinant(Bike bike);
}
