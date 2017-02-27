package com.gplucky.task.service.impl;

import com.gplucky.common.bean.Parameters;
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
import java.util.Set;
import java.util.stream.Stream;

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
    private String redis_key_seq_max;
    @Value("${seq_start_num}")
    private String seq_start_num;
    @Autowired
    private StockHistoryService stockHistoryService;

    /**
     * 根据当日最新涨跌 计算出最新的连涨和连跌
     *
     * @return
     */
    @Override
    public boolean autoStockSeqUpAndDown() {
        for (int i = Integer.valueOf(redis_key_seq_max); i >= 0; i--) {
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
        if (Integer.valueOf(redis_key_seq_max) == keySuffix) {
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
        cleanInitSeqUpAndDown();
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
        for(int i=1;i<= Integer.valueOf(redis_key_seq_max); i++){
            if(i == Integer.valueOf(redis_key_seq_max)){
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
        LOG.info("初始化历史涨跌数据中…………");
        final int startNum = Integer.valueOf(seq_start_num);
        for (int i = startNum; i < (Integer.valueOf(redis_key_seq_max)+1); i++) {
            int num = i;
            List<StockHistory> list = stockHistoryService.selectStockHistoryByDate(DateUtils.lastWorkingDay(i));
            LOG.info("num={}, size={}", num, list.size());
            list.stream().forEach(stockHistory -> {
                double changepercent = Double.valueOf(stockHistory.getChangepercent());
                String code = stockHistory.getSymbol();
                LOG.debug("code={}，涨跌幅={}", code, changepercent);
                if (changepercent > 0) {
                    //涨
                    LOG.debug("涨，code={}", code);
                    LOG.debug("num={}", num);
                    if (num == startNum) {
                        //初始化0和1
                        redisUtils.sadd(StockExt.SEQ_UP_0, code);
                        redisUtils.sadd(StockExt.SEQ_UP_1, code);
                        LOG.debug(StockExt.SEQ_UP_0);
                        LOG.debug(StockExt.SEQ_UP_1);
                    }else{
                        redisUtils.sadd(StockExt.SEQ_INIT_UP_PREFIX + (num - startNum + 1), code);
                        LOG.debug(StockExt.SEQ_INIT_UP_PREFIX + (num - startNum + 1));
                    }
                } else if (changepercent < 0) {
                    //跌
                    LOG.debug("跌，code={}", code);
                    if (num == startNum) {
                        //初始化0和1
                        redisUtils.sadd(StockExt.SEQ_DOWN_0, code);
                        redisUtils.sadd(StockExt.SEQ_DOWN_1, code);
                        LOG.debug(StockExt.SEQ_DOWN_0);
                        LOG.debug(StockExt.SEQ_DOWN_1);
                    }else{
                        redisUtils.sadd(StockExt.SEQ_INIT_DOWN_PREFIX + (num - startNum + 1), code);
                        LOG.debug(StockExt.SEQ_INIT_DOWN_PREFIX + (num - startNum + 1));
                    }
                }
            });
        }
        LOG.info("…………初始化历史涨跌数据完成");
    }

    private void cleanInitSeqUpAndDown() {
        LOG.info("清空缓存连涨连跌数据中…………");
        for (int i = 0; i <= Integer.valueOf(redis_key_seq_max); i++) {
            if (i == 0) {
                redisUtils.delete(StockExt.SEQ_INIT_UP_PREFIX + (Integer.valueOf(redis_key_seq_max)+1));
                redisUtils.delete(StockExt.SEQ_INIT_DOWN_PREFIX + (Integer.valueOf(redis_key_seq_max)+1));
                LOG.debug(StockExt.SEQ_INIT_UP_PREFIX + (Integer.valueOf(redis_key_seq_max)+1));
                LOG.debug(StockExt.SEQ_INIT_DOWN_PREFIX + (Integer.valueOf(redis_key_seq_max)+1));
            }else{
                redisUtils.delete(StockExt.SEQ_INIT_UP_PREFIX + i);
                redisUtils.delete(StockExt.SEQ_INIT_DOWN_PREFIX + i);
                LOG.debug(StockExt.SEQ_INIT_UP_PREFIX + i);
                LOG.debug(StockExt.SEQ_INIT_DOWN_PREFIX + i);
            }
        }
        LOG.info("…………清空缓存连涨连跌数据完成");
    }

    public void cleanSeqUpAndDown() {
        LOG.info("清空连涨连跌数据中…………");
        for (int i = 0; i <= Integer.valueOf(redis_key_seq_max); i++) {
            if (i == 0) {
                redisUtils.delete(StockExt.SEQ_UP_0);
                redisUtils.delete(StockExt.SEQ_DOWN_0);
                redisUtils.delete(StockExt.SEQ_UP_PLUS);
                redisUtils.delete(StockExt.SEQ_DOWN_PLUS);
                LOG.debug(StockExt.SEQ_UP_0);
                LOG.debug(StockExt.SEQ_DOWN_0);
                LOG.debug(StockExt.SEQ_UP_PLUS);
                LOG.debug(StockExt.SEQ_DOWN_PLUS);
            }else{
                redisUtils.delete(StockExt.SEQ_UP_PREFIX + i);
                redisUtils.delete(StockExt.SEQ_DOWN_PREFIX + i);
                LOG.debug(StockExt.SEQ_UP_PREFIX + i);
                LOG.debug(StockExt.SEQ_DOWN_PREFIX + i);
            }
        }
        LOG.info("…………清空连涨连跌数据完成");
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

    /**
     * 得到连涨股票代码
     * @param num
     * @param parameters
     * @return
     */
    @Override
    public Stream<Object> getSeqUpByDays(int num, Parameters parameters) {
        String key = getSeqUpByDaysKey(num);
        LOG.info("getSeqUpByDays, key={}", key);
        Set<Object> set = redisUtils.smembers(key);
        return filterSeqUp(set, parameters);
    }

    /**
     * 对股票连涨信息进行过滤
     * @param set
     * @param parameters
     * @return
     */
    private Stream<Object> filterSeqUp(Set<Object> set, Parameters parameters) {
        Stream<Object> stream = Stream.empty();
        //是否显示创业板
        if(parameters.isGrowth()){
            stream = filterGrowth(set);
        }
        return stream;
    }

    /**
     * 过滤创业板
     * @param set
     * @return
     */
    private Stream<Object> filterGrowth(Set<Object> set) {
        return set.stream()
                .filter(d -> !((String)d).startsWith("sh300")&&!((String)d).startsWith("sz300"));
    }

    /**
     * 得到连涨的key
     * @param num
     * @return
     */
    private String getSeqUpByDaysKey(int num) {
        String key = StockExt.SEQ_UP_PREFIX;
        if(num >= Integer.valueOf(redis_key_seq_max)){
            key = StockExt.SEQ_UP_PLUS;
        }else{
            key += num;
        }
        return key;
    }
}
