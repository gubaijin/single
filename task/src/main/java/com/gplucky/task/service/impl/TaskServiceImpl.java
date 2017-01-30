package com.gplucky.task.service.impl;

import com.gplucky.task.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public void aaa() {
        System.out.println(111);
    }
}
