package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.ext.StockExt;
import com.gplucky.common.utils.RedisUtils;
import com.gplucky.task.service.StockRedisService;
import com.gplucky.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehsy_it on 2017/2/5.
 */
@Service
public class StockRedisServiceImpl implements StockRedisService {
    private static final Logger LOG = LoggerFactory.getLogger(StockRedisServiceImpl.class);

    @Autowired
    private StockService stockService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean initStockSeqUpAndDown() {
        List<Stock> list = stockService.getStockList();
        list.stream().forEach(stock -> {
            double changepercent = Double.valueOf(stock.getChangepercent());
            String code = stock.getCode();
            LOG.debug("code={}，涨跌幅={}", code, changepercent);
            if (changepercent > 0) {
                //涨
                LOG.debug("涨，code={}", code);
                redisUtils.sadd(StockExt.SEQ_UP_1, code);
            } else if (changepercent < 0) {
                //跌
                LOG.debug("跌，code={}", code);
                redisUtils.sadd(StockExt.SEQ_DOWN_1, code);
            }
        });
        return true;
    }
}
