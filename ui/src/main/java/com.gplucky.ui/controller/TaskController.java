package com.gplucky.ui.controller;

import com.gplucky.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ehsy_it on 2017/3/11.
 */
@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/task")
    public String task() {
        return "task";
    }

    @RequestMapping("/init1")
    @ResponseBody
    public String init1(String pwd) {
        return taskService.initSHList(pwd);
    }

    @RequestMapping("/init2")
    @ResponseBody
    public String init2(String pwd) {
        return taskService.initSZList(pwd);
    }

    @RequestMapping("/init3")
    @ResponseBody
    public String init3(String pwd) {
        return taskService.initStockUpAndDown(pwd);
    }

    @RequestMapping("/init4")
    @ResponseBody
    public String init4(String pwd) {
        return taskService.initStockToMongo(pwd);
    }

    @RequestMapping("/base1")
    @ResponseBody
    public String base1() {
        return taskService.fetchStockInfo();
    }

    @RequestMapping("/base2")
    @ResponseBody
    public String base2() {
        return taskService.fetchCompensation();
    }
    
}
