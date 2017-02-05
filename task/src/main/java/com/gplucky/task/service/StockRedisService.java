package com.gplucky.task.service;

/**
 * Created by ehsy_it on 2017/2/5.
 */
public interface StockRedisService {

    /**
     * 初始化股票连涨及连跌
     */
    boolean initStockSeqUpAndDown();

}
