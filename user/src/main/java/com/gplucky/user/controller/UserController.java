package com.gplucky.user.controller;

import com.gplucky.common.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/22.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value="获取用户信息列表", notes="获取用户信息列表")
    @RequestMapping(value="getUserInfoList", method= RequestMethod.GET)
    public ResponseEntity<String> getUserInfoList() {
        LOG.info("获取用户信息");
        List<User> list = new ArrayList<>();
        User user1 = new User();
        user1.setName("用户1");
        user1.setAge("11");
        list.add(user1);
        User user2 = new User();
        user2.setName("用户2");
        user2.setAge("22");
        list.add(user2);
        return this.returnSuccessMsg(list);
    }

/*    @ApiOperation(value="得到用户信息", notes="根据id和name得到详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = true, dataType = "String")
    })
    @RequestMapping(value="getInfo", method=RequestMethod.POST)
    @ResponseBody
    public String getInfo(Long id, String name) {
        LOG.info("得到用户信息");
        return userMapper.selectByPrimaryKey(1).getName();
    }*/
}
