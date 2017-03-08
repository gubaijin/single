package com.gplucky.ui.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@Component
public class UserHystrixService implements UserService{

    @Override
    public String register(@RequestParam(value = "user") String user) {
        return "login";
    }
}
