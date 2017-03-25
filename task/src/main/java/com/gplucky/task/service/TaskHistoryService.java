package com.gplucky.task.service;

/**
 * Created by ehsy_it on 2017/1/25.
 */
public interface TaskHistoryService {
    Long insertStartTask(String type);

    void updateFinishedTask(Long taskId);
}
