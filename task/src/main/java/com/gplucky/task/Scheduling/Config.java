package com.gplucky.task.Scheduling;

import com.gplucky.common.mybatis.model.ext.StockExt;
import com.gplucky.common.mybatis.model.ext.TaskHistoryExt;
import com.gplucky.task.service.StockRedisService;
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
    private final Logger LOG = LoggerFactory.getLogger(Config.class);
    @Autowired
    private StockService stockService;

    @Autowired
    private TaskHistoryService taskHistoryService;

    @Autowired
    private StockRedisService stockRedisService;

    /**
     * 周一至周五的下午18:00触发
     */
    @Scheduled(cron = "0 30 11 ? * MON-FRI")
    public void fetchStockInfo() {
        /*int taskId = taskHistoryService.insertStartTask(TaskHistoryExt.TYPE_FETCHSTOCKINFO);
        LOG.info("定时任务(同步股票信息)，开始…………");
        stockService.fetchStockInfo();
        LOG.info("…………结束，定时任务结束(同步股票信息)");
        taskHistoryService.updateFinishedTask((long) taskId);*/
        System.out.println("定时任务执行");
    }

    /**
     * 周一至周五的下午19:00触发
     */
    @Scheduled(cron = "0 0 19 ? * MON-FRI")
    public void initTask() {
        int taskId = taskHistoryService.insertStartTask(TaskHistoryExt.TYPE_INITTASK);
        LOG.info("定时任务(初始化任务)，开始…………");
        LOG.info("筛选初始化当天涨跌redis数据中…………");
        stockRedisService.initStockSeqUpAndDown(StockExt.SEQ_UP_0, StockExt.SEQ_DOWN_0);
        LOG.info("…………筛选初始化当天涨跌redis数据完成。");
        LOG.info("更新连涨连跌数据中…………");
        stockRedisService.autoStockSeqUpAndDown();
        LOG.info("…………更新连涨连跌数据完成。");
        LOG.info("…………结束，定时任务结束(初始化任务)");
        taskHistoryService.updateFinishedTask((long) taskId);
    }
}
