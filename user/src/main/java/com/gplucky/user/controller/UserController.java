package com.gplucky.user.controller;

import com.gplucky.common.controller.BaseController;
import com.gplucky.common.mybatis.dao.UserMapper;
import com.gplucky.common.mybatis.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lenovo on 2017/1/22.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value="获取用户信息", notes="根据id获取用户信息")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="getInfoById", method= RequestMethod.POST)
    public ResponseEntity<String> getInfoById(User user) {
        LOG.info("获取用户信息");
        return this.returnSuccessMsg();
    }

    @ApiOperation(value="得到用户信息", notes="根据id和name得到详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = true, dataType = "String")
    })
    @RequestMapping(value="getInfo", method=RequestMethod.POST)
    @ResponseBody
    public String getInfo(Long id, String name) {
        LOG.info("得到用户信息");
        return userMapper.selectByPrimaryKey(1).getName();
    }
}
