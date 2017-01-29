package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.dao.StockHistoryMapper;
import com.gplucky.common.mybatis.model.StockHistory;
import com.gplucky.task.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ehsy_it on 2017/1/29.
 */
@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    @Autowired
    private StockHistoryMapper stockHistoryMapper;

    @Override
    public boolean insert(StockHistory record) {
        stockHistoryMapper.insertSelective(record);
        return true;
    }
}
