package com.gplucky.user.service;

import com.gplucky.common.mybatis.model.User;

/**
 * Created by ehsy_it on 2017/3/4.
 */
public interface UserService {

    boolean register(User user);

    boolean isExist(String username);

    boolean login(User userObj);
}
