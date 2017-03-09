package com.gplucky.ui.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@FeignClient(value = "user")
public interface UserService {

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    ResponseEntity<String> register(@RequestParam(value = "user") String user);

    @RequestMapping(method = RequestMethod.POST, value = "/isExistUsername")
    ResponseEntity<String> isExistUsername(@RequestParam(value = "username") String username);

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    ResponseEntity<String> login(@RequestParam(value = "user") String user);

}
