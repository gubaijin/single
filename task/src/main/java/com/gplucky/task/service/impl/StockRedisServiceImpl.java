package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.ext.StockExt;
import com.gplucky.common.utils.RedisUtils;
import com.gplucky.task.service.StockRedisService;
import com.gplucky.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${redis_key_seq_max}")
    private int redis_key_seq_max;

    /**
     * 根据当日最新涨跌 计算出最新的连涨和连跌
     * @return
     */
    @Override
    public boolean autoStockSeqUpAndDown() {
        for(int i = redis_key_seq_max;i >= 0; i-- ){
            loopStockSeqUpOrDown(i);
        }
        return true;
    }

    /**
     * 最新与20取交集，并存入super key中，删除key20
     * 最新与19取交集，并存入20 key中，删除key19
     * ………………
     * 最新与1取交集，并存入2 key中，删除key1
     * 将最新存入1 key中，删除key0
     * @param keySuffix
     * @return
     */
    private void loopStockSeqUpOrDown(int keySuffix){
        LOG.info("更新连涨连跌{}", keySuffix);
        if(redis_key_seq_max == keySuffix){
            //20
            redisUtils.sIntersectAndStore(StockExt.SEQ_UP_0, StockExt.SEQ_UP_PREFIX + keySuffix,
                    StockExt.SEQ_UP_PLUS);
            redisUtils.delete(StockExt.SEQ_UP_PREFIX + keySuffix);
            redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_0, StockExt.SEQ_DOWN_PREFIX + keySuffix,
                    StockExt.SEQ_DOWN_PLUS);
            redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + keySuffix);
        }else if(0 == keySuffix){
            //0
            redisUtils.sIntersectAndStore(StockExt.SEQ_UP_0, StockExt.SEQ_UP_0,
                    StockExt.SEQ_UP_1);
            redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_0, StockExt.SEQ_DOWN_0,
                    StockExt.SEQ_DOWN_1);
            redisUtils.delete(StockExt.SEQ_UP_PREFIX + keySuffix);
            redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + keySuffix);
        }else{
            //涨
            redisUtils.sIntersectAndStore(StockExt.SEQ_UP_0, StockExt.SEQ_UP_PREFIX + keySuffix,
                    StockExt.SEQ_UP_PREFIX + (keySuffix + 1));
            redisUtils.delete(StockExt.SEQ_UP_PREFIX + keySuffix);
            //跌
            redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_0, StockExt.SEQ_DOWN_PREFIX + keySuffix,
                    StockExt.SEQ_DOWN_PREFIX + (keySuffix + 1));
            redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + keySuffix);
        }
    }



    @Override
    public boolean initStockSeqUpAndDown(String seqUpKey, String seqDownKey) {
        List<Stock> list = stockService.getStockList();
        list.stream().forEach(stock -> {
            double changepercent = Double.valueOf(stock.getChangepercent());
            String code = stock.getCode();
            LOG.debug("code={}，涨跌幅={}", code, changepercent);
            if (changepercent > 0) {
                //涨
                LOG.debug("涨，code={}", code);
                redisUtils.sadd(seqUpKey, code);
            } else if (changepercent < 0) {
                //跌
                LOG.debug("跌，code={}", code);
                redisUtils.sadd(seqDownKey, code);
            }
        });
        return true;
    }
}
