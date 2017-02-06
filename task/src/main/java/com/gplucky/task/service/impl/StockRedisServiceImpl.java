package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.StockHistory;
import com.gplucky.common.mybatis.model.ext.StockExt;
import com.gplucky.common.utils.DateUtils;
import com.gplucky.common.utils.RedisUtils;
import com.gplucky.task.service.StockHistoryService;
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
    @Autowired
    private StockHistoryService stockHistoryService;

    /**
     * 根据当日最新涨跌 计算出最新的连涨和连跌
     *
     * @return
     */
    @Override
    public boolean autoStockSeqUpAndDown() {
        for (int i = redis_key_seq_max; i >= 0; i--) {
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
     *
     * @param keySuffix
     * @return
     */
    private void loopStockSeqUpOrDown(int keySuffix) {
        LOG.info("更新连涨连跌{}", keySuffix);
        if (redis_key_seq_max == keySuffix) {
            //20
            redisUtils.sIntersectAndStore(StockExt.SEQ_UP_0, StockExt.SEQ_UP_PREFIX + keySuffix,
                    StockExt.SEQ_UP_PLUS);
            redisUtils.delete(StockExt.SEQ_UP_PREFIX + keySuffix);
            redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_0, StockExt.SEQ_DOWN_PREFIX + keySuffix,
                    StockExt.SEQ_DOWN_PLUS);
            redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + keySuffix);
        } else if (0 == keySuffix) {
            //0
            redisUtils.sIntersectAndStore(StockExt.SEQ_UP_0, StockExt.SEQ_UP_0,
                    StockExt.SEQ_UP_1);
            redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_0, StockExt.SEQ_DOWN_0,
                    StockExt.SEQ_DOWN_1);
            redisUtils.delete(StockExt.SEQ_UP_PREFIX + keySuffix);
            redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + keySuffix);
        } else {
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
    public boolean initStockSeqUpAndDown() {
        cleanSeqUpAndDown();
        loopStockHistoryGetSeqInitUpAndDown();
        setInitStockSeqUpAndDown();
        return true;
    }

    /**
     * 比对历史单日涨跌，取交集生成连涨连跌（seq_up_0\seq_up_1\seq_down_0\seq_down_1已经初始化过了，不考虑）
     * 比较：seq_up_1 与 seq_init_up_2 取交集  生成seq_up_2
     *      seq_up_2 与 seq_init_up_3 取交集 生成seq_up_3
     *      ………………………………
     *      seq_up_19 与 seq_init_up_20 取交集 生成seq_up_20
     *      seq_up_20 与 seq_init_up_21 取交集 生成seq_up_plus
     *
     *      down相同
     */
    private void setInitStockSeqUpAndDown() {
        for(int i=1;i<= redis_key_seq_max; i++){
            if(i == redis_key_seq_max){
                redisUtils.sIntersectAndStore(StockExt.SEQ_UP_PREFIX + i, StockExt.SEQ_INIT_UP_PREFIX + (i + 1),
                        StockExt.SEQ_UP_PLUS);
                redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_PREFIX + i, StockExt.SEQ_INIT_DOWN_PREFIX + (i + 1),
                        StockExt.SEQ_DOWN_PLUS);
            }else {
                redisUtils.sIntersectAndStore(StockExt.SEQ_UP_PREFIX + i, StockExt.SEQ_INIT_UP_PREFIX + (i + 1),
                        StockExt.SEQ_UP_PREFIX + (i + 1));
                redisUtils.sIntersectAndStore(StockExt.SEQ_DOWN_PREFIX + i, StockExt.SEQ_INIT_DOWN_PREFIX + (i + 1),
                        StockExt.SEQ_DOWN_PREFIX + (i + 1));
            }
        }
    }

    /**
     * 这里从0开始，方便日期获取，0为当天
     * 总共往前redis_key_seq_max+1天（+1是为了获得plus）
     */
    private void loopStockHistoryGetSeqInitUpAndDown() {
        for (int i = 0; i < (redis_key_seq_max+1); i++) {
            final int num = i;
            List<StockHistory> list = stockHistoryService.selectStockHistoryByDate(DateUtils.lastWorkingDay(i));
            list.stream().forEach(stockHistory -> {
                double changepercent = Double.valueOf(stockHistory.getChangepercent());
                String code = stockHistory.getCode();
                LOG.debug("code={}，涨跌幅={}", code, changepercent);
                if (changepercent > 0) {
                    //涨
                    LOG.debug("涨，code={}", code);
                    if (num == 0) {
                        //初始化0和1
                        redisUtils.sadd(StockExt.SEQ_UP_0, code);
                        redisUtils.sadd(StockExt.SEQ_UP_1, code);
                    }else{
                        redisUtils.sadd(StockExt.SEQ_INIT_UP_PREFIX + num + 1, code);
                    }
                } else if (changepercent < 0) {
                    //跌
                    LOG.debug("跌，code={}", code);
                    if (num == 1) {
                        //初始化0和1
                        redisUtils.sadd(StockExt.SEQ_DOWN_0, code);
                        redisUtils.sadd(StockExt.SEQ_DOWN_1, code);
                    }else{
                        redisUtils.sadd(StockExt.SEQ_INIT_DOWN_PREFIX + num + 1, code);
                    }
                }
            });
        }
    }

    private void cleanSeqUpAndDown() {
        for (int i = 0; i <= redis_key_seq_max; i++) {
            if (i == 0) {
                redisUtils.delete(StockExt.SEQ_UP_PLUS);
                redisUtils.delete(StockExt.SEQ_DOWN_PLUS);
            }
            redisUtils.delete(StockExt.SEQ_UP_PREFIX + i);
            redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + i);
            LOG.info("清空连涨连跌数据中，key{}={}", i, StockExt.SEQ_UP_PREFIX + i);
            LOG.info("清空连涨连跌数据中，key{}={}", i, StockExt.SEQ_UP_PREFIX + i);
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
