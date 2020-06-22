package com.yc.projects.ibike.bean;

import java.io.Serializable;
import java.util.Arrays;

public class Bike  implements Serializable {
    private Long bid;
    private int status;
    private String qrcode;
    private Double latitude;
    private Double longitude;

    private Long id;
    private Double [] loc=new Double[2];

    public static final int UNACTIVE=0;
    public static final int LOCK=1;
    public static final int USING=2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double[] getLoc() {
        return loc;
    }

    public void setLoc(Double[] loc) {
        this.loc = loc;
    }

    public static final  int INTROUBLE=3;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Double getLatitude() {
        return latitude;
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "bid=" + bid +
                ", status=" + status +
                ", qrcode='" + qrcode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", id=" + id +
                ", loc=" + Arrays.toString(loc) +
                '}';
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
