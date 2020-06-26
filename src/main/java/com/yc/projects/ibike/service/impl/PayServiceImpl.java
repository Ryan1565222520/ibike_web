package com.yc.projects.ibike.service.impl;

import com.yc.projects.ibike.bean.Bike;
import com.yc.projects.ibike.bean.PayModel;
import com.yc.projects.ibike.bean.User;
import com.yc.projects.ibike.service.BikeService;
import com.yc.projects.ibike.service.PayService;
import com.yc.projects.ibike.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @Author Ryan_Tang
 * @Date 2020/6/26 15:13
 */
@Service
@Transactional    //添加事务   方法出错可以回滚
public class PayServiceImpl implements PayService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BikeService bikeService;
    @Autowired
    private UserService userService;

    private Logger logger= LogManager.getLogger();

    public static final int MONEYPERHOUR=4;

    @Override
    public void pay(PayModel payModel) {
        //计算金额
        Long startTime=payModel.getStartTime();
        Long endTime=payModel.getEndTime();
        Long spendTime=endTime-startTime;
        double hours=Double.parseDouble(spendTime+"")/(60*60);
        Integer h=(int)Math.ceil(hours);

        int pay=h*MONEYPERHOUR;
        logger.info("所用的小时"+h +"钱"+pay);
        payModel.setPayMoney(pay);
        payModel.setLogTime(new Date().toLocaleString());

       // 将数据保存到mongo的 payLog ( uuid,phoneNum,openId, 结账时间(年月日小时) 起(经纬),时间, 止(经纬) ,时间, 花费)
        this.mongoTemplate.insert(    payModel,   "payLog"    );

        //修改单车的状态为1   和经纬度
        Query q=new Query();
        q.addCriteria(Criteria.where("id").is(payModel.getBid()));
        Update u=new Update();
        u.set("latitude",payModel.getLatitude());
        u.set("longitude",payModel.getLongitude());
        Double[] loc=new Double[]{payModel.getLatitude(),payModel.getLongitude()};
        u.set("loc",loc).set("status", Bike.LOCK);
        mongoTemplate.updateFirst(q,u,Bike.class,"bike");

        //修改用户的状态为3   以及扣钱
        Query qq=new Query();
        qq.addCriteria(Criteria.where("phoneNum").is(payModel.getPhoneNum()));
        User user=mongoTemplate.findOne(qq, User.class, "users");;
        Update uu=new Update().set("status",3);
        uu.set("balance",( user.getBalance()-pay));
        mongoTemplate.updateFirst(qq,uu,User.class,"users");
        logger.info("结账成功");

    }
}
