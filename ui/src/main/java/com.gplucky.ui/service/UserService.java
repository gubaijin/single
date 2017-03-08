package com.gplucky.ui.service;

import com.gplucky.common.mybatis.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@FeignClient(value = "user", fallback = UserHystrixService.class)
public interface UserService {

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    String register(@RequestParam(value = "user") String user);

}
