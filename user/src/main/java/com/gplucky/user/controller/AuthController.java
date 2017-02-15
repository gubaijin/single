package com.gplucky.user.controller;

import com.gplucky.common.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ehsy_it on 2017/2/13.
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @ApiOperation(value="用户认证", notes="用户认证")
    @RequestMapping(value="info", method= RequestMethod.GET)
    public ResponseEntity<String> info(String token) {
        LOG.info("用户认证");
        /*String userName = "gbj";
        String pwd = "123";
        String temp = Base64Utils.encodeToString((userName + ", " + pwd).getBytes());
        if(token.equals(temp)){
            User user = new User();
            user.setName(userName);
            return this.returnSuccessMsg(user);
        }else{
            return this.returnSuccessMsg();
        }*/
        return this.returnSuccessMsg();
    }

}
