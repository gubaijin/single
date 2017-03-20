package com.gplucky.task.service;

import com.gplucky.common.mybatis.model.Stock;

import java.util.List;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public interface StockService {

    /**
     * 得到股票列表
     * @return
     */
    List<Stock> getStockList();

    /**
     * 抓取最新的股市信息
     * @return
     */
    boolean fetchStockInfo();

    boolean initSHList();

    boolean initSZList();

    /**
     * 失败补偿同步股列表
     * @return
     */
    boolean fetchCompensation();

    /**
     * 查询股票信息
     * @param stock
     * @return
     */
    List<Stock> select(Stock stock);

    /**
     * 查询所有股票code
     * @param stock
     * @return
     */
    List<String> selectAllCode(Stock stock);
}
