package com.yc.projects.ibike.web.controller;

import com.yc.projects.ibike.service.LogService;
import com.yc.projects.ibike.web.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogController {


    @Autowired
    private LogService logService;

    @RequestMapping("/log/savelog")
    public  @ResponseBody JsonModel ready(JsonModel jsonModel,@RequestBody String log){
        logService.save(log);
        jsonModel.setCode(1);
        return jsonModel;
    }
}
