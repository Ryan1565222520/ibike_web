package com.yc.projects.ibike.service.Impl;

import com.yc.projects.ibike.bean.Bike;
import com.yc.projects.ibike.dao.BikeDao;
import com.yc.projects.ibike.service.BikeService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional   //加入事务
@Api(value="YY出行单业务操作接口",tags = {"单车信息","业务层"})
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeDao bikeDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    private Logger logger=LogManager.getLogger();

    @Override
    public void open(Bike bike) {
        if(bike.getBid()==null){
            throw new RuntimeException("缺少待开无此单车编号");
        }
        Bike b=bikeDao.findBike(bike.getBid());
        if(b==null){
            throw new RuntimeException("查无此车");
        }
        switch(b.getStatus()){
            case Bike.UNACTIVE:
                throw new RuntimeException("此车暂未启用 请更换车辆");
            case Bike.USING:
                throw new RuntimeException("此车正在使用 请更换车辆");
            case Bike.INTROUBLE:
                throw new RuntimeException("此车待维修 请更换车辆");
        }
        bike.setStatus(Bike.USING);
        bike.setQrcode(bike.getBid()+"");
        bikeDao.updateBike(bike);
    }

    @Override
    @Transactional(readOnly = true)    //只读事务
    public Bike findByBid(Long bid) {
        Bike b=null;
        try {
            b=bikeDao.findBike(bid);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return b;
    }

    @Override
    public Bike addNewBike(Bike bike) {
        Bike b=bikeDao.addBike(bike);
        Long bid=b.getBid();
        bike=findByBid(bid);
        //生成二维码
        String qrcode=bid+"";
        bike.setQrcode(qrcode);
        bikeDao.updateBike(bike);
        return bike;
    }

    @Override
    public List<Bike> findNearAll(Bike bike) {
        Query query=new Query();
        query.addCriteria(Criteria.where("status").is(bike.getStatus()))
                .addCriteria(Criteria.where("loc").near(new Point(bike.getLatitude(),bike.getLongitude())))
                .limit(10);
        List<Bike> list=this.mongoTemplate.find(query,Bike.class,"bike");

        for (Bike b:list){
            b.setBid(b.getId());
            b.setId(null);
            b.setLatitude(b.getLoc()[0]);
            b.setLongitude(b.getLoc()[1]);
            b.setLoc(null);
        }
        return list;
    }
}
