package com.yc.projects.ibike.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(value= JsonInclude.Include.NON_NULL)
@ApiModel(value = "结果响应实体",description = "所有的REST调用得到的json结果")
public class JsonModel implements Serializable {

    private static final long serialVersionUID = 2880873342154063305L;
    @ApiModelProperty(value="操作响应码 ，一般1表示成功操作，其他均为失败",required = true)
    private Integer code;
    @ApiModelProperty(value="操作的响应信息 如code为0，则为异常信息")
    private String msg;
    @ApiModelProperty(value="操作的结果 code为1，则为结果值")
    private Object obj;
    @ApiModelProperty(value="本次操作执行后，下一步的重定向的地址")
    private String url;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JsonModel [code=" + code + ", msg=" + msg + ", obj=" + obj + ", url=" + url + "]";
    }

}