package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.dao.StockHistoryMapper;
import com.gplucky.common.mybatis.model.StockHistory;
import com.gplucky.common.mybatis.model.StockHistoryExample;
import com.gplucky.common.utils.DateUtils;
import com.gplucky.task.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ehsy_it on 2017/1/29.
 */
@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    @Autowired
    private StockHistoryMapper stockHistoryMapper;

    @Override
    public List<StockHistory> selectStockHistoryByDate(LocalDate localDate) {
        StockHistoryExample example = new StockHistoryExample();
        example.createCriteria().andCreateTimeBetween(DateUtils.getDateStart(localDate),
                DateUtils.getDateEnd(localDate));
        return stockHistoryMapper.selectByExample(example);
    }

    @Override
    public boolean insert(StockHistory record) {
        stockHistoryMapper.insertSelective(record);
        return true;
    }

    @Override
    public boolean isExistByCodeNowDate(String code, LocalDate localDate) {
        StockHistoryExample example = new StockHistoryExample();
        example.createCriteria().andCodeEqualTo(code)
                .andCreateTimeBetween(DateUtils.getDateStart(localDate), DateUtils.getDateEnd(localDate));
        int count = stockHistoryMapper.countByExample(example);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<StockHistory> getTradeList(String code, int num) {
        StockHistoryExample example = new StockHistoryExample();
        example.createCriteria().andCodeEqualTo(code);
        example.setOrderByClause("update_time desc limit " + num);
        return stockHistoryMapper.selectByExample(example);
        /*if(!CollectionUtils.isEmpty(list)){
            return list.stream().map(stockHistory ->
                Double.valueOf(stockHistory.getTrade())
            ).collect(Collectors.toList());
        }else{
            return null;
        }*/
    }
}
