package com.gplucky.task.service;

import com.gplucky.common.mybatis.model.StockHistory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ehsy_it on 2017/1/29.
 */
public interface StockHistoryService {

    /**
     * 查询某一天的故事信息
     * @param localDate
     * @return
     */
    List<StockHistory> selectStockHistoryByDate(LocalDate localDate);

    boolean insert(StockHistory record);

    boolean isExistByCodeNowDate(String code, LocalDate localDate);
}
