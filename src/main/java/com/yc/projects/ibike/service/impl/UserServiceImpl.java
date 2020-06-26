package com.yc.projects.ibike.service.impl;

import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import com.yc.projects.ibike.bean.User;
import com.yc.projects.ibike.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger=Logger.getLogger(UserServiceImpl.class);

    // 操作redis中的v是对象类型的数据
    @Autowired
    private RedisTemplate redisTemplate;
    // 操作redis中的字符串类型数据
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String redisSessionKey(String openId, String sessionKey) {
        String rsession= UUID.randomUUID().toString();
        //首先根据openid，取出之前存的openid对应的sessionKey的值
        String oldSessionKey=stringRedisTemplate.opsForValue().get(openId);
        if(oldSessionKey !=null && ! "".equals(oldSessionKey)){
            logger.info("oldSessionKey:"+sessionKey);
            //删除之前openid对应的缓存
            stringRedisTemplate.delete(openId);
            logger.info("老的openId删除以后==" + stringRedisTemplate.opsForValue().get(oldSessionKey));
        }
        //开始缓存新的sessionKey  格式:  { uuid:{ "openid":openId,"sessionKey":sessionKey }  }
        Gson g=new Gson();
        Map<String,String> m=new HashMap<String,String>();
        m.put("openid",openId);
        m.put("sessionKey", sessionKey);
        String s=g.toJson(m);
        //stringRedisTemplate.opsForValue().set(rsession, s, 30*24*60*60, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(rsession, s, 5*60, TimeUnit.SECONDS);

        //开始缓存新的opneid与session对应关系 {openid ,ression}
        //stringRedisTemplate.opsForValue().set(openId,rsession,30*24*60*60,TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(openId,rsession,5*60,TimeUnit.SECONDS);

        return rsession;
    }

    @Override
    public void addMember(User u) {
        mongoTemplate.insert(u);
    }

    @Override
    public List<User> selectMember(String openid) {
        Query q=new Query(Criteria.where("openid").is(openid));
        return this.mongoTemplate.find(q,User.class,"users");
    }

    @Override
    public void genVerifyCode(String nationCode, String phoneNum) {
        // 短信接口的 appkey
        // String appkey = stringRedisTemplate.opsForValue().get("appkey");
        // 生成验证码
        String code=(int)((Math.random()*9+1)*1000)+"";
        logger.info("生成的验证码为："+code);
        // SmsUtils.sendSms(code, new String[] {nationCode+phoneNum});    //TODO: 以后发送
        // redisTemplate.
        // 将数据保存到redis中，redis的key手机号，value是验证码，有效时长120秒
        stringRedisTemplate.opsForValue().set(phoneNum, code, 120, TimeUnit.SECONDS);
    }

    /**
     * 验证验证码并注册用户1
     * @param user
     * @return
     */
    @Override
    public boolean verify(User user) {
        boolean flag=false;
        String phoneNum=user.getPhoneNum();
        String verifyCode=user.getVerifyCode();
        String code=stringRedisTemplate.opsForValue().get(phoneNum);
        //System.out.println(verifyCode+code);
        String openid=user.getOpenid();
        String uuid=user.getUuid();

        int status=1;
        if(verifyCode!=null && verifyCode.equals(code)){
            UpdateResult result=mongoTemplate.updateFirst(new Query(Criteria.where("openid").is(openid)),new Update().set("status",1).set("phoneNum",phoneNum),User.class);
            if(result.getModifiedCount()==1){
               return true;
            }
        }
        return flag;
    }

    @Override
    public boolean deposit(User user) {
        int status=2;
        int money=299;
        UpdateResult result=mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())),
                new Update().set("status",status).set("deposit",money),User.class);
        if(result.getModifiedCount()==1){
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public boolean identity(User user) {
        //TODO:调用第三方接口公安身份证验证接口
        int status=3;
        UpdateResult reuslt=mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())),
                new Update().set("status",status).set("name",user.getName()).set("idNum",user.getIdNum()),User.class);
        if(reuslt.getModifiedCount()==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean recharge(double balance, String phoneNum) {
        boolean flag=false;
        try {
            mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(phoneNum)),
                    new Update().inc("balance",balance),User.class);
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


}
