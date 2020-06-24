package com.yc.projects.ibike.dao;

import com.yc.projects.ibike.bean.Bike;


public interface BikeDao {

    /**
     * 新增一新车测入库
     * @param bike
     * @return
     */
    public Bike addBike(Bike bike);

    /**
     * 更新操作  解锁
     * @param bike
     */
    public void updateBike(Bike bike);

    /**
     * 查找车辆
     * @param bike
     * @return
     */
    public Bike findBike (String bid);



}
