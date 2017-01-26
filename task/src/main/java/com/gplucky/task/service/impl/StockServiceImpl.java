package com.gplucky.task.service.impl;

import com.google.common.collect.Maps;
import com.gplucky.common.transport.RequestUtil;
import com.gplucky.common.transport.data.RespData;
import com.gplucky.task.bean.StockResult;
import com.gplucky.task.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ehsy_it on 2017/1/26.
 */
@Service
public class StockServiceImpl implements StockService{

    @Value("${juhe.stock.appkey}")
    private String APP_KEY;

    @Value("${juhe.url.stock.list.sh}")
    private String STOCK_URL_LIST_SH;

    @Override
    public RespData<StockResult> getSHList() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("key", APP_KEY);
        params.put("page", "");
        RespData<StockResult> respData = RequestUtil.createDefault().get(STOCK_URL_LIST_SH, params, StockResult.class);
        return respData;
    }

}
