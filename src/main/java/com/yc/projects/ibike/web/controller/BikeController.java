package com.yc.projects.ibike.web.controller;

import com.yc.projects.ibike.bean.Bike;
import com.yc.projects.ibike.service.BikeService;
import com.yc.projects.ibike.web.model.JsonModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

//@RestController -> @Controller @ResponseBody 返回json
@Controller
@Api(value="YY出行单车信息操作接口",tags = {"单车信息","控制层"})
public class BikeController {
    @Autowired
    private BikeService bikeService;

    private Logger logger = LogManager.getLogger();

    /**
     * 扫码开锁
     * @param jsonModel 返回值部分
     * @param bike  必须的参数  bid  经度  纬度
     * @return
     */
    @RequestMapping(value="/open",method = {RequestMethod.POST})
    @ApiOperation(value="用户开锁操作",notes = "给指定的共享单车开锁 ，参数以json格式传过来") //方法说明
    public @ResponseBody  JsonModel open(@ApiIgnore JsonModel jsonModel, @RequestBody Bike bike) { //@ApiIgnore  在swagger界面不显示参数
        logger.info("open请求参数--》"+bike);
        try {
            Bike b=bikeService.findByBid(bike.getBid());
            if(b==null){
                jsonModel.setCode(0);
                jsonModel.setMsg("查无此车 ");
            }else{
                switch(b.getStatus()){
                    case Bike.UNACTIVE:
                        jsonModel.setCode(0);
                        jsonModel.setMsg("此车暂未启用 ");
                    case Bike.USING:
                        jsonModel.setCode(0);
                        jsonModel.setMsg("此车正在使用");
                    case Bike.INTROUBLE:
                        jsonModel.setCode(0);
                        jsonModel.setMsg("此车待维修 ");
                }
                bikeService.open(bike);
                jsonModel.setCode(1);
            }
        } catch (Exception e) {
            jsonModel.setCode(0);
            jsonModel.setMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return jsonModel;

    }

    @RequestMapping(value="/findNearAll",method = {RequestMethod.POST})
    @ApiOperation(value="查找最近单车",notes = "查找最近的40部单车") //方法说明
    public @ResponseBody JsonModel findNearAll(@ApiIgnore JsonModel jsonModel,@RequestBody Bike bike){
        List<Bike> list=bikeService.findNearAll(bike);
        jsonModel.setCode(1);
        jsonModel.setObj(list);
        return jsonModel;
    }

    @PostMapping("/repair")
    public @ResponseBody JsonModel repair(   JsonModel jsonModel, Bike bike) {
        try {
            this.bikeService.reportMantinant(  bike );
            jsonModel.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonModel.setCode(0);;
            jsonModel.setMsg(  e.getMessage() );
        }
        return jsonModel;
    }
}