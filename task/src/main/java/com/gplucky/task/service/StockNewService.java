package com.gplucky.task.service;

import com.gplucky.common.mybatis.model.StockNew;

import java.util.List;

/**
 * Created by ehsy_it on 2017/2/4.
 */
public interface StockNewService {
    boolean insert(StockNew record);

    List<StockNew> selectAll();
}
