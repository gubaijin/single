package com.gplucky.task.controller;

import com.gplucky.task.service.StockRedisService;
import com.gplucky.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ehsy_it on 2017/2/5.
 */
@Controller
@RequestMapping("init")
public class InitController {

    private static final Logger LOG = LoggerFactory.getLogger(InitController.class);

    @Autowired
    private StockService stockService;
    @Autowired
    private StockRedisService stockRedisService;

//    @ApiOperation(value="初始化股票连涨连跌", notes="初始化连涨连跌到redis中,切记！！先将股市最新数据及历史数据同步后执行！！")
    @RequestMapping(value="initStockUpAndDown", method = RequestMethod.POST)
    @ResponseBody
    public String initStockUpAndDown(){
        LOG.info("初始化股票连涨连跌开始……");
        boolean flg = stockRedisService.initStockSeqUpAndDown();
        LOG.info("……结束初始化股票连涨连跌");
        if(flg){
            return "success";
        }else{
            return "failed";
        }
    }

//    @ApiOperation(value="初始化沪股列表", notes="初始化所有沪股信息")
    @RequestMapping(value="initSHList", method = RequestMethod.POST)
    @ResponseBody
    public String initSHList(){
        LOG.info("初始化沪股列表开始……");
        boolean flg = stockService.initSHList();
        LOG.info("……结束初始化沪股列表");
        if(flg){
            return "success";
        }else{
            return "failed";
        }
    }

//    @ApiOperation(value="初始化深圳股市列表", notes="初始化所有深圳股市信息")
    @RequestMapping(value="initSZList", method = RequestMethod.POST)
    @ResponseBody
    public String initSZList(){
        LOG.info("初始化深圳股市列表开始……");
        boolean flg = stockService.initSZList();
        LOG.info("……结束初始化深圳股市列表");
        if(flg){
            return "success";
        }else{
            return "failed";
        }
    }
}
