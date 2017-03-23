package com.gplucky.parameter.controller;

import com.gplucky.parameter.service.RSIService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.gplucky.common.bean.Password.PWD;

/**
 * Created by ehsy_it on 2017/3/21.
 */
@RestController
@RequestMapping(value = "init")
public class InitController {

    private static final Logger LOG = LoggerFactory.getLogger(InitController.class);

    @Autowired
    private RSIService rsiService;

    @ApiOperation(value="手工初始化所有股票的RSI指标", notes="先从task获取所有的股票信息，遍历获取历史信息并计算")
    @ApiImplicitParam(paramType = "query",name = "pwd", value = "敏感接口密码", required = true, dataType = "String")
    @RequestMapping(value="rsi", method = RequestMethod.POST)
    @ResponseBody
    public String rsi(String pwd){
        LOG.info("手工初始化所有股票的RSI指标开始……");
        if(PWD.getValue().equals(pwd)){
            boolean flg = rsiService.initRSI();
            LOG.info("……结束手工初始化所有股票的RSI指标");
            if(flg){
                return "success";
            }else{
                return "failed";
            }
        }else{
            return "failed";
        }
    }
}
