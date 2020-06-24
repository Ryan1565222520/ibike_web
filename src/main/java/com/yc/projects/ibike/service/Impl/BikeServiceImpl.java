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
import org.springframework.data.mongodb.core.query.Update;
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
    public Bike findByBid(String bid) {
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
        String bid=b.getBid();
        bike=findByBid(bid);
        //生成二维码
        String qrcode=bid;
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

    @Override
    public void reportMantinant(Bike bike) {
        //1.根据bid查出车的状态, 要报修的车不能是行驶状态 2
        Query query=new Query();
        query.addCriteria(Criteria.where("id").is(bike.getBid()));
        Bike torepair=mongoTemplate.findOne(query,Bike.class,"bike");
        //Bike torepair=mongoTemplate.findById(bike.getBid(),Bike.class,"bike")
        if(torepair==null){
            throw  new RuntimeException("查无此车登记:"+bike.getBid()+"    请重试！");
        }
        if(torepair.getStatus()==Bike.USING){
            throw new RuntimeException("该车："+bike.getBid()+"  正在使用状态，为了您的安全，请锁车后再报修！");
        }
        //2. 将此信息存入到  mongo中，并加入一个状态  handleStatus: 0 暂未处理  1已经处理
        mongoTemplate.insert(bike,"torepairbikes");
        //      以后处理完了，要加入  handler 处理人   handleTime 处理时间
        //3. 将此车的状态在  bike collection中更改为 3
        Update u=new Update();
        u.set("status",Bike.INTROUBLE);
        mongoTemplate.updateFirst(query,u,Bike.class,"bike");
    }
}
