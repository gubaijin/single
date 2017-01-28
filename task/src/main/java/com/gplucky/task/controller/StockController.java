package com.gplucky.task.controller;

import com.gplucky.common.controller.BaseController;
import com.gplucky.task.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ehsy_it on 2017/1/28.
 */
@Controller
@RequestMapping("stock")
public class StockController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value="初始化沪股列表", notes="初始化所有沪股信息")
    @RequestMapping(value="initSHList")
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
    @ApiOperation(value="初始化深圳股市列表", notes="初始化所有深圳股市信息")
    @RequestMapping(value="initSZList")
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
