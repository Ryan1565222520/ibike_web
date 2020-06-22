package com.yc.projects.ibike.service.Impl;

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
}
