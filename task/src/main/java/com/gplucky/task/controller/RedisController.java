package com.gplucky.task.controller;

import com.gplucky.common.controller.BaseController;
import com.gplucky.task.service.StockRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ehsy_it on 2017/2/6.
 */
@Controller
@RequestMapping("redis")
public class RedisController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private StockRedisService stockRedisService;

    @RequestMapping("getInfo")
    public ResponseEntity<String> getInfo(){
        return this.returnSuccessMsg();
    }

}
