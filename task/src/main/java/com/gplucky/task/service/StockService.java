package com.gplucky.task.service;

import com.gplucky.common.transport.data.RespData;
import com.gplucky.task.bean.StockResult;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public interface StockService {

    RespData<StockResult> getSHList();
}
