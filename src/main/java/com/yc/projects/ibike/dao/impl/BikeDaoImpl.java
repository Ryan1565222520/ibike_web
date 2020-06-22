package com.yc.projects.ibike.dao.impl;

import com.yc.projects.ibike.bean.Bike;
import com.yc.projects.ibike.dao.BikeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BikeDaoImpl  implements BikeDao {
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDateSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public Bike addBike(Bike bike) {
        String sql="insert into bike(latitude,longitude,status,qrcode) values(0.0 ,0.0,0, '')";
        KeyHolder keyHolder=new GeneratedKeyHolder(); //函数自增
        this.jdbcTemplate.update(connection->{
            PreparedStatement ps=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return ps;
        },keyHolder);
        bike.setBid(keyHolder.getKey().longValue());
        return bike;
    }

    @Override
    public void updateBike(Bike bike) {
        String sql=new String("update bike set latitude=?,longitude=?,status=?,qrcode=? where bid=?");
        this.jdbcTemplate.update(connection->{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setDouble(1,bike.getLatitude());
            ps.setDouble(2,bike.getLongitude());
            ps.setInt(3,bike.getStatus());
            ps.setString(4,bike.getQrcode());
            ps.setLong(5,bike.getBid());
            return ps;
        });
    }

    @Override
    public Bike findBike(Long bid) {
        String sql="select * from bike where bid="+bid;
        return (Bike) this.jdbcTemplate.queryForObject(sql,(resultSet, rowNum) ->{
            Bike b=new Bike();
            b.setBid(resultSet.getLong("bid"));
            b.setLatitude( resultSet.getDouble("latitude"));
            b.setLongitude(resultSet.getDouble("longitude"));
            b.setQrcode(resultSet.getString("qrcode"));
            b.setStatus(resultSet.getInt("status"));
            return b;
        });
    }
}
