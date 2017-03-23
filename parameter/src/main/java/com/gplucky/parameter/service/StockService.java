package com.gplucky.parameter.service;

import com.gplucky.common.bean.mongo.StockM;

import java.util.List;

/**
 * Created by ehsy_it on 2017/3/20.
 */
public interface StockService {

    public List<StockM> getStockList();
}
