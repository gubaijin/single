package com.gplucky.task.Scheduling;

import com.gplucky.common.mybatis.model.ext.TaskHistoryExt;
import com.gplucky.task.service.StockService;
import com.gplucky.task.service.TaskHistoryService;
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
    private StockService stockService;

    @Autowired
    private TaskHistoryService taskHistoryService;

    private final Logger LOG = LoggerFactory.getLogger(Config.class);

    /**
     * 周一至周五的下午19:00触发
     */
    @Scheduled(cron = "0 0 19 ? * MON-FRI")
    public void fetchStockInfo() {
        int taskId = taskHistoryService.insertStartTask(TaskHistoryExt.TYPE_FETCHSTOCKINFO);
        LOG.info("定时任务(同步股票信息)，开始…………");
        stockService.fetchStockInfo();
        LOG.info("…………结束，定时任务结束(同步股票信息)");
        taskHistoryService.updateFinishedTask((long) taskId);
    }
}
