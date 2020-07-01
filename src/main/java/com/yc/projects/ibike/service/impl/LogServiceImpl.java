package com.yc.projects.ibike.service.impl;

import com.yc.projects.ibike.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(String log) {
        mongoTemplate.save(log,"logs");
    }

    @Override
    public void savePayLog(String log) {
        mongoTemplate.save(log,"paylogs");
    }

    @Override
    public void saveRepairLog(String log) { mongoTemplate.save(log,"repairlogs"); }

    @Override
    public void saveUseLog(String log) { mongoTemplate.save(log,"uselogs"); }
}
