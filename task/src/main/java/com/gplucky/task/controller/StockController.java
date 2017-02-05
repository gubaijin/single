package com.gplucky.task.controller;

import com.gplucky.common.controller.BaseController;
import com.gplucky.task.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ehsy_it on 2017/1/28.
 */
@Controller
@RequestMapping("stock")
public class StockController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value="手动同步沪深股市信息", notes="先同步沪股再同步深股，并记录到股市列表")
    @RequestMapping(value="fetchStockInfo", method = RequestMethod.POST)
    @ResponseBody
    public String fetchStockInfo(){
        LOG.info("手动同步股列表开始……");
        boolean flg = stockService.fetchStockInfo();
        LOG.info("……结束手动同步股列表");
        if(flg){
            return "success";
        }else{
            return "failed";
        }
    }

    @ApiOperation(value="失败补偿手动同步沪深股市信息", notes="由于同步失败，失败补偿每次都会比较记录是否已经存在")
    @RequestMapping(value="fetchCompensation", method = RequestMethod.POST)
    @ResponseBody
    public String fetchCompensation(){
        LOG.info("失败补偿同步股列表开始……");
        boolean flg = stockService.fetchCompensation();
        LOG.info("……结束失败补偿同步股列表");
        if(flg){
            return "success";
        }else{
            return "failed";
        }
    }

}
