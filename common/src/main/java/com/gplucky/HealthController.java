package com.gplucky;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/1/22.
 */
@RestController
public class HealthController {
    @RequestMapping("/health")
    public String health(){
        return "success";
    }

}
