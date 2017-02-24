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

    @RequestMapping("/health")
    public String health(){
        return env;
    }

}
