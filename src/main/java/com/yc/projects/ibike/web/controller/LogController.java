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

    //记录登录日志
    @RequestMapping("/log/savelog")
    public  @ResponseBody JsonModel ready(JsonModel jsonModel,@RequestBody String log){
        logService.save(log);
        jsonModel.setCode(1);
        return jsonModel;
    }

    //记录充值日志
    @RequestMapping("/log/addPayLog")
    public  @ResponseBody JsonModel savePayLogs(JsonModel jsonModel,@RequestBody String log){
        logService.savePayLog(log);
        jsonModel.setCode(1);
        return jsonModel;
    }

    //记录报修日志
    @RequestMapping("/log/addRepairLog")
    public  @ResponseBody JsonModel saveRepairLogs(JsonModel jsonModel,@RequestBody String log){
        logService.saveRepairLog(log);
        jsonModel.setCode(1);
        return jsonModel;
    }

    //记录单车使用日志
    @RequestMapping("/log/addUseLog")
    public  @ResponseBody JsonModel saveUseLogs(JsonModel jsonModel,@RequestBody String log){
        logService.saveUseLog(log);
        jsonModel.setCode(1);
        return jsonModel;
    }


}
