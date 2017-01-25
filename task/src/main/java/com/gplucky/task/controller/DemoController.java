package com.gplucky.task.controller;

import com.gplucky.common.mybatis.dao.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value="获取用户信息", notes="根据id获取用户信息")
    @RequestMapping(value="test")
    public String getdd(){
        return userMapper.selectByPrimaryKey(1).getName();
    }
}
