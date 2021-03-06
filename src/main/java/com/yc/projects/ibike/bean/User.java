package com.yc.projects.ibike.bean;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 这个类对应了mongo一个文档
 */
@Document(collection = "users")
public class User implements Serializable {

    private int status;   //用户的状态:  0 刚注册   1 押金缴纳   2. 实名认证
    //这个字段创建索引
    //@Indexed(unique = true)
    private String phoneNum;
    private String name;   //用户名
    private String idNum;   //身份证
    private double deposit;  //押金
    private double balance;  //余额。

    //这个属性不在数据库中储存
    @Transient  //瞬态化
    private  String verifyCode;

    private String openid;
    private String uuid;

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getOpenid() { return openid; }

    public void setOpenid(String openid) { this.openid = openid; }

    @Override
    public String toString() {
        return "User{" +
                "status=" + status +
                ", phoneNum='" + phoneNum + '\'' +
                ", name='" + name + '\'' +
                ", idNum='" + idNum + '\'' +
                ", deposit=" + deposit +
                ", balance=" + balance +
                ", verifyCode='" + verifyCode + '\'' +
                ", openid='" + openid + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
