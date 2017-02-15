package com.gplucky.ui.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@FeignClient("task")
public interface TaskService {
    @RequestMapping(method = RequestMethod.GET, value = "/task/getInfo")
    String getInfo();
}
