package com.gplucky.user.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.controller.BaseController;
import com.gplucky.common.mybatis.model.User;
import com.gplucky.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/1/22.
 */
@RestController
public class UserController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "注册新用户")
    @ApiImplicitParam(paramType = "body",name = "user", value = "用户信息JSON", required = true, dataType = "String")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(String user) {
        User userObj = JSON.parseObject(user, User.class);
        if (userService.isExist(userObj.getUsername())) {
            return "redirect:/register";
        } else {
            userService.register(userObj);
            return "redirect:/login";
        }
    }
}
