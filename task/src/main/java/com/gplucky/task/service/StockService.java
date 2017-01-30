package com.gplucky.task.service;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public interface StockService {

    /**
     * 得到最新的股市信息
     * @return
     */
    boolean fetchStockInfo();

    boolean initSHList();

    boolean initSZList();

}
