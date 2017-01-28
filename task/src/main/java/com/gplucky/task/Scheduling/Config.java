package com.gplucky.task.Scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by ehsy_it on 2017/1/25.
 */
@Configuration
@EnableScheduling
public class Config {

    private final Logger LOG = LoggerFactory.getLogger(Config.class);

    /*@Scheduled(cron = "0/5 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        LOG.info(">>>>>>>>>>>>> scheduled ... ");
    }*/
}
