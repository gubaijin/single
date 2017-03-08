package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.mybatis.model.User;
import com.gplucky.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ehsy_it on 2017/3/1.
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String toIndex() {
        return "forward:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/page1")
    public String page1() {
        return "page1";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register( User user ) {
        return "register";
    }

    @RequestMapping("/postRegister")
    public String postRegister( User user ) {
        return userService.register(JSON.toJSONString(user));
    }

    @RequestMapping("/404")
    public String error404() {
        return "error/404";
    }

    @RequestMapping("/500")
    public String error500() {
        return "error/500";
    }
}
