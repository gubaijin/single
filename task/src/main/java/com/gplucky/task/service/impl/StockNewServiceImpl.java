package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.dao.StockNewMapper;
import com.gplucky.common.mybatis.model.StockNew;
import com.gplucky.task.service.StockNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ehsy_it on 2017/2/4.
 */
@Service
public class StockNewServiceImpl implements StockNewService {

    @Autowired
    private StockNewMapper stockNewMapper;

    @Override
    public boolean insert(StockNew record) {
        stockNewMapper.insertSelective(record);
        return true;
    }
}
