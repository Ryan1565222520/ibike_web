package com.yc.projects.ibike.bean;

import java.io.Serializable;

/**
 * @Author Ryan_Tang
 * @Date 2020/6/26 15:09
 */
public class PayModel implements Serializable {
    private Double longitude;
    private Double latitude;
    private String uuid;  //关于用户会话的id 一个小程序里唯一的  相当于sessionId
    private String openid;   //用openid不安全    还可以访问别的小程序 用uuid就可以全部查出来
    private String phoneNum;
    private Long startTime;
    private Long endTime;
    private Long totalTime;

    private Integer payMoney;
    private String logTime;
    private String bid;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "PayModel{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", uuid='" + uuid + '\'' +
                ", openid='" + openid + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalTime=" + totalTime +
                ", payMoney=" + payMoney +
                ", logTime='" + logTime + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}

