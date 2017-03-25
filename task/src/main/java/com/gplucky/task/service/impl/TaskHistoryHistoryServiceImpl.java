package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.dao.TaskHistoryMapper;
import com.gplucky.common.mybatis.model.TaskHistory;
import com.gplucky.common.mybatis.model.ext.TaskHistoryExt;
import com.gplucky.task.service.TaskHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@Service
public class TaskHistoryHistoryServiceImpl implements TaskHistoryService {

    @Autowired
    private TaskHistoryMapper taskHistoryMapper;

    @Override
    public Long insertStartTask(String type) {
        TaskHistory taskHistory = new TaskHistory();
        Date date = new Date();
        taskHistory.setType(type);
        taskHistory.setStatus(TaskHistoryExt.STATUS_START);
        taskHistory.setCreateTime(date);
        taskHistory.setUpdateTime(date);
        taskHistoryMapper.insertSelective(taskHistory);
        return taskHistory.getId();
    }

    @Override
    public void updateFinishedTask(Long taskId) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(taskId);
        taskHistory.setStatus(TaskHistoryExt.STATUS_FINISHED);
        taskHistory.setUpdateTime(new Date());
        taskHistoryMapper.updateByPrimaryKeySelective(taskHistory);
    }
}
