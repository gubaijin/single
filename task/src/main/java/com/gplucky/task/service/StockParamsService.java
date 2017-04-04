package com.gplucky.task.service;

import com.gplucky.common.mybatis.model.StockParams;
import com.gplucky.common.mybatis.model.StockParamsExample;

import java.util.List;

/**
 * Created by ehsy_it on 2017/4/4.
 */
public interface StockParamsService {
    List<StockParams> selectAll();

    int updateByExampleSelective(StockParams record, StockParamsExample example);

    StockParams getBySymbol(String symbol);

    List<StockParams> select(StockParams stockParams);

    boolean resetStockUpAndDown();

    void upAndDownResetZero();

    void updateUpAndDownDays();
}
