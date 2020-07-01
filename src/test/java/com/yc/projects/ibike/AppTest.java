package com.yc.projects.ibike;

import ch.qos.logback.core.CoreConstants;
import com.yc.projects.ibike.bean.Bike;
import com.yc.projects.ibike.config.AppConfig;
import com.yc.projects.ibike.dao.BikeDao;
import com.yc.projects.ibike.service.BikeService;
import com.yc.projects.ibike.service.UserService;
import com.yc.projects.ibike.utils.SmsUtils;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class AppTest extends TestCase {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BikeDao bikeDao;
    @Autowired
    private BikeService bikeService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void testUserService() throws Exception {
        userService.genVerifyCode("86", "18707339505");
    }

    @Test
    public void send(){
        SmsUtils.sendSms("1234", new String[] {"8618707339505"});
    }

    @Test
    public void addBikes(){
        Bike b;
        for(int i=0;i<199999;i++){
            b=new Bike();
            double random1 = ThreadLocalRandom.current().nextDouble(0, 90);
            double random2 = ThreadLocalRandom.current().nextDouble(0, 180);
            Double [] loc={ random1,random2};
            b.setStatus(Bike.LOCK);
            b.setQrcode("");
            b.setLoc(loc);
            mongoTemplate.save(b,"bike");
            System.out.println(i);
        }

    }


    @Test
    public void testRedisTemplate() {
        stringRedisTemplate.opsForValue().set("hello", "world");
        stringRedisTemplate.opsForValue().set("hello2", "world");
    }



    @Test
    public void testMongoTemplate(){
        System.out.println("+++"+mongoTemplate.getDb().getName());
    }

    @Test
    public void testDataSourve() throws SQLException {
        assertNotNull(dataSource);
        assertNotNull(dataSource.getConnection());

    }

    @Test
    public void testFindNearAll(){
        Bike bike=new Bike();
        bike.setLatitude(28.189122);
        bike.setLongitude(112.943867);
        bike.setStatus(Bike.LOCK);
        List<Bike> list=bikeService.findNearAll(bike);
        System.out.println("--------"+list);
    }

    @Test
    public void testAddNewBike(){
        Bike b=new Bike();
        Bike result=bikeDao.addBike(b);
        assertNotNull(result.getBid());
        System.out.println("新增的单车的id是："+result.getBid());
    }

    @Test
    public void testUpdateBike(){
        Bike b=bikeDao.findBike("");
        b.setLatitude(20.9);
        b.setLongitude(22.2);
        b.setStatus(1);
        bikeDao.updateBike(b);
    }

    @Test
    public void testServiceOpen(){
        Bike b=bikeService.findByBid("");
        bikeService.open(b);
    }

    @Test
    public void testServiceAddNewBike(){
        Bike b=new Bike();
        Bike result=bikeService.addNewBike(b);
        System.out.println(result.getQrcode());
    }
}
