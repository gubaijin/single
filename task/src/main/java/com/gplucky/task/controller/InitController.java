package com.gplucky.task.controller;

import com.gplucky.task.service.StockRedisService;
import com.gplucky.task.service.StockService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ehsy_it on 2017/2/5.
 */
@Controller
@RequestMapping("init")
public class InitController {

    private static final Logger LOG = LoggerFactory.getLogger(InitController.class);

    private static final String PWD = "gbj";

    @Autowired
    private StockService stockService;
    @Autowired
    private StockRedisService stockRedisService;

    @ApiOperation(value = "测试hello", notes = "测试hello的接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query",name = "userId", value = "用户id", required = true, dataType = "String"),
                    @ApiImplicitParam(paramType = "query",name = "name", value = "用户名", required = true, dataType = "String")}
    )
    @RequestMapping(value = "hello", method = RequestMethod.POST)
    @ResponseBody
    public String hello(@RequestParam(value = "userId") String userId, @RequestParam(value = "name") String name) {
        return "hello" + userId + name;
    }

    @ApiOperation(value="初始化股票连涨连跌", notes="初始化连涨连跌到redis中,切记！！先将股市最新数据及历史数据同步后执行！！")
    @ApiImplicitParam(paramType = "query",name = "pwd", value = "敏感接口密码", required = true, dataType = "String")
    @RequestMapping(value="initStockUpAndDown", method = RequestMethod.POST)
    @ResponseBody
    public String initStockUpAndDown(String pwd){
        LOG.info("初始化股票连涨连跌开始……");
        if(PWD.equals(pwd)){
            boolean flg = stockRedisService.initStockSeqUpAndDown();
            LOG.info("……结束初始化股票连涨连跌");
            if(flg){
                return "success";
            }else{
                return "failed";
            }
        }else{
            return "failed";
        }
    }

    @ApiOperation(value="初始化沪股列表", notes="初始化所有沪股信息")
    @ApiImplicitParam(paramType = "query",name = "pwd", value = "敏感接口密码", required = true, dataType = "String")
    @RequestMapping(value="initSHList", method = RequestMethod.POST)
    @ResponseBody
    public String initSHList(String pwd){
        LOG.info("初始化沪股列表开始……");
        if(PWD.equals(pwd)){
            boolean flg = stockService.initSHList();
            LOG.info("……结束初始化沪股列表");
            if(flg){
                return "success";
            }else{
                return "failed";
            }
        }else{
            return "failed";
        }
    }

    @ApiOperation(value="初始化深圳股市列表", notes="初始化所有深圳股市信息")
    @ApiImplicitParam(paramType = "query",name = "pwd", value = "敏感接口密码", required = true, dataType = "String")
    @RequestMapping(value="initSZList", method = RequestMethod.POST)
    @ResponseBody
    public String initSZList(String pwd){
        LOG.info("初始化深圳股市列表开始……");
        if(PWD.equals(pwd)){
            boolean flg = stockService.initSZList();
            LOG.info("……结束初始化深圳股市列表");
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
