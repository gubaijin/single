package com.gplucky.user;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/1/22.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static User user1, user2;
    static{
        user1 = new User();
        user1.setId(1);
        user1.setName("顾柏进");
        user2 = new User();
        user1.setId(2);
        user1.setName("杨钱");
    }

    @ApiOperation(value="获取用户信息", notes="根据id获取用户信息")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="getInfoById", method= RequestMethod.POST)
    public User getInfoById(User user) {
        if(1 == user.getId()){
            return user1;
        }else{
            return user2;
        }
    }

    @ApiOperation(value="得到用户信息", notes="根据id和name得到详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = true, dataType = "String")
    })
    @RequestMapping(value="getInfo", method=RequestMethod.POST)
    public String getInfo(Long id, String name) {
        return "1111";
    }
}
