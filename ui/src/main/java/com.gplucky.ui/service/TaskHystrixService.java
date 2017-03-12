package com.gplucky.ui.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.controller.BaseController;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by ehsy_it on 2017/2/14.
 */
/*@Component
public class TaskHystrixService implements TaskService{*/
@Component
public class TaskHystrixService{

//    @Override
    public String getSeqUpByDays(Integer num, Integer pageNo, String filterParameters){
        HttpResult httpResult = new HttpResult();
        httpResult.setMark(BaseController.FAILED_MARK);
        Set<String> set = Sets.newHashSet();
        set.add("暂无数据");
        httpResult.setData(set);
        return JSON.toJSONString(httpResult);
    }
}
