package com.gplucky.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/1/22.
 */
@RestController
public class HealthController {
    @Value("${env}")
    private String env;

    @Value("${juhe.stock.appkey}")
    private String APP_KEY;

    @Value("${juhe.url.stock.list.sh}")
    private String STOCK_URL_LIST_SH;

    @Value("${juhe.url.stock.list.sz}")
    private String STOCK_URL_LIST_SZ;

    @RequestMapping("/health")
    public String health(){
        return env + APP_KEY + STOCK_URL_LIST_SH + STOCK_URL_LIST_SZ;
    }

}
