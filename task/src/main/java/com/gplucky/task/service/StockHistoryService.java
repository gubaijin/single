package com.gplucky.task.service;

import com.gplucky.common.mybatis.model.StockHistory;

import java.time.LocalDate;

/**
 * Created by ehsy_it on 2017/1/29.
 */
public interface StockHistoryService {

    boolean insert(StockHistory record);

    boolean isExistByCodeNowDate(String code, LocalDate localDate);
}
