package com.gplucky.task;

import com.gplucky.common.utils.RedisUtils;
import com.gplucky.task.service.impl.StockRedisServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ehsy_it on 2017/1/30.
 */
@RunWith(SpringRunner.class)
@ImportResource({"classpath:/common.xml"})
@SpringBootTest
@Configuration
@ComponentScan("com.gplucky")
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private StockRedisServiceImpl stockRedisService;
    @Test
    public void set() {

        /*redisUtils.set("gbj", "211");
        System.out.println(redisUtils.get("gbj"));*/

        stockRedisService.cleanSeqUpAndDown();

    }
}
