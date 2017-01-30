package com.gplucky.task.Scheduling;

import com.gplucky.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@Configuration
@ComponentScan("com.gplucky.task.service")
@EnableScheduling
public class Config {

    @Autowired
    private TaskService taskService;

    private final Logger LOG = LoggerFactory.getLogger(Config.class);

    /**
     * 周一至周五的下午15:30触发
     */
    @Scheduled(cron = "0 30 15 ? * MON-FRI")
    public void scheduler() {
        taskService.aaa();
    }
}
