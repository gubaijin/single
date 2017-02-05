package com.gplucky.task.service;

/**
 * Created by ehsy_it on 2017/2/5.
 */
public interface StockRedisService {

    /**
     * 根据当日最新涨跌 计算出最新的连涨和连跌
     * @return
     */
    boolean autoStockSeqUpAndDown();

    /**
     * 初始化股票连涨及连跌
     * @param seqUpKey
     * @param seqDownKey
     */
    boolean initStockSeqUpAndDown(String seqUpKey, String seqDownKey);

}
