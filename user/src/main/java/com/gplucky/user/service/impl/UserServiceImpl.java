package com.gplucky.user.service.impl;

import com.gplucky.common.mybatis.dao.UserMapper;
import com.gplucky.common.mybatis.model.User;
import com.gplucky.common.mybatis.model.UserExample;
import com.gplucky.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ehsy_it on 2017/3/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setPassword(DigestUtils.md5Hex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return true;
    }

    public boolean isExist(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean login(User userObj) {
        String username = userObj.getUsername();
        String pwd = userObj.getPassword();
        String password = getPwdByUsername(username);
        if(password.equals(DigestUtils.md5Hex(pwd.getBytes()))){
            return true;
        }else{
            return false;
        }
    }

    private String getPwdByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return "";
        }else{
            return list.get(0).getPassword();
        }
    }
}
