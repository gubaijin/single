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
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<String> register(String user) {
        User userObj = JSON.parseObject(user, User.class);
        if (userService.isExist(userObj.getUsername())) {
            return this.returnFailMsg("用户已经存在");
        } else {
            userService.register(userObj);
            return this.returnSuccessMsg();
        }
    }

    @ApiOperation(value = "检查用户名是否存在", notes = "检查用户名是否存在")
    @ApiImplicitParam(paramType = "query",name = "username", value = "用户名", required = true, dataType = "String")
    @RequestMapping(value = "isExistUsername", method = RequestMethod.POST)
    public ResponseEntity<String> isExistUsername(String username) {
        if (userService.isExist(username)) {
            return this.returnSuccessMsg(true);
        } else {
            return this.returnSuccessMsg(false);
        }
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(paramType = "body",name = "user", value = "JSON：用户名、密码", required = true, dataType = "String")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> login(String user) {
        User userObj = JSON.parseObject(user, User.class);
        if(null == userObj || StringUtils.isEmpty(userObj.getUsername().trim())
                || StringUtils.isEmpty(userObj.getPassword().trim())){
            return this.returnFailMsg("账号或密码不能为空");
        }else{
            if(userService.login(userObj)){
                return this.returnSuccessMsg();
            }else{
                return this.returnFailMsg("账号或密码错误");
            }
        }
    }
}
