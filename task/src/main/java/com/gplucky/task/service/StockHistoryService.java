package com.gplucky.task.service;

import com.gplucky.common.mybatis.model.StockHistory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ehsy_it on 2017/1/29.
 */
public interface StockHistoryService {

    /**
     * 查询某一天的股票信息
     * @param localDate
     * @return
     */
    List<StockHistory> selectStockHistoryByDate(LocalDate localDate);

    boolean insert(StockHistory record);

    boolean isExistByCodeNowDate(String code, LocalDate localDate);

    /**
     * 得到某个股票多少天内的股价(倒序)
     * @param code
     * @param num
     * @return
     */
    List<StockHistory> getTradeList(String code, int num);

    /**
     * 根据股票代码查询股票历史信息(升序)
     * @param code
     * @return
     */
    List<StockHistory> selectByCode(String code);
}
