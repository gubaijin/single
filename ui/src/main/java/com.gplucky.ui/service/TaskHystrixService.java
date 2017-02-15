package com.gplucky.ui.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@Service
public class TaskHystrixService {
    @Autowired
    private TaskService taskService;

    @HystrixCommand(fallbackMethod = "getDefault")
    public String getInfo(){
        return taskService.getInfo();
    }

    public String getDefault(){
        return "success";
    }
}
