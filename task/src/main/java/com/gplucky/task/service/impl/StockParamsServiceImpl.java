package com.gplucky.task.service.impl;

import com.gplucky.common.mybatis.dao.StockParamsMapper;
import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.StockParams;
import com.gplucky.common.mybatis.model.StockParamsExample;
import com.gplucky.task.feign.MessageFeign;
import com.gplucky.task.service.StockParamsService;
import com.gplucky.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ehsy_it on 2017/4/4.
 */
@Service
public class StockParamsServiceImpl implements StockParamsService {
    private static final Logger LOG = LoggerFactory.getLogger(StockParamsServiceImpl.class);

    @Value("${message.mail.task.to}")
    private String MESSAGE_MAIL_TASK_TO;
    @Value("${message.mail.title3}")
    private String MESSAGE_MAIL_TITLE3;
    @Value("${message.mail.content3}")
    private String MESSAGE_MAIL_CONTENT3;

    @Autowired
    private StockParamsMapper stockParamsMapper;

    @Autowired
    private StockService stockService;
    @Autowired
    private MessageFeign messageFeign;

    @Override
    public List<StockParams> selectAll() {
        return stockParamsMapper.selectByExample(null);
    }

    @Override
    public int updateByExampleSelective(StockParams record, StockParamsExample example) {
        return stockParamsMapper.updateByExampleSelective(record, example);
    }

    @Override
    public StockParams getBySymbol(String symbol) {
        StockParamsExample example = new StockParamsExample();
        example.createCriteria().andSymbolEqualTo(symbol);
        List<StockParams> list = stockParamsMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }else{
            return list.get(0);
        }
    }

    /**
     * 查询股票指标信息
     * @param stockParams
     * @return
     */
    @Override
    public List<StockParams> select(StockParams stockParams) {
        return stockParamsMapper.selectByExample(convertExample(stockParams));
    }

    /**
     * 重置股票连涨连跌
     * @return
     */
    @Override
    public boolean resetStockUpAndDown() {
        LOG.info("连涨连跌归零中…………");
        upAndDownResetZero();
        LOG.info("…………连涨连跌已归零");
        updateUpAndDownDays();
        return true;
    }

    /**
     * 连涨连跌归零
     */
    @Override
    public void upAndDownResetZero() {
        StockParams stockParams = new StockParams();
        stockParams.setUpDays(0);
        stockParams.setDownDays(0);
        stockParams.setUpdateTime(new Date());
        StockParamsExample example = new StockParamsExample();
        example.createCriteria().andSymbolIsNotNull();
        stockParamsMapper.updateByExampleSelective(stockParams, example);
    }

    private StockParamsExample convertExample(StockParams stockParams) {
        StockParamsExample example = new StockParamsExample();
        StockParamsExample.Criteria criteria = example.createCriteria();
        String symbol = stockParams.getSymbol();
        if(!StringUtils.isEmpty(symbol)){
            criteria.andSymbolLike("%" + symbol + "%");
        }
        Integer upDays = stockParams.getUpDays();
        if(null != upDays && 0 != upDays){
            criteria.andUpDaysEqualTo(upDays);
        }
        Integer downDays = stockParams.getDownDays();
        if(null != downDays && 0 != downDays){
            criteria.andDownDaysEqualTo(downDays);
        }
        example.setOrderByClause("up_days desc, down_days desc");
        return example;
    }

    /**
     * 更新连涨连跌数据
     */
    @Override
    public void updateUpAndDownDays() {
        try {
            LOG.info("更新连涨连跌数据中…………");
            Date date = new Date();
            List<Stock> stockList = stockService.getStockList();
            stockList.stream().parallel().forEach(stock -> {
                String symbol = stock.getSymbol();
                StockParams params = getBySymbol(symbol);
                double changepercent = Double.valueOf(stock.getChangepercent());
                if (changepercent > 0) {
                    //涨
                    Integer up = params.getUpDays();
                    params.setUpDays(up + 1);
                    params.setDownDays(0);
                } else if (changepercent < 0) {
                    //跌
                    Integer down = params.getDownDays();
                    params.setUpDays(0);
                    params.setDownDays(down + 1);
                }
                StockParamsExample example = new StockParamsExample();
                example.createCriteria().andSymbolEqualTo(symbol);
                params.setUpdateTime(date);
                updateByExampleSelective(params, example);
            });
            LOG.info("…………连涨连跌数据已更新");
            messageFeign.sendSimpleMail(MESSAGE_MAIL_TASK_TO, MESSAGE_MAIL_TITLE3, MESSAGE_MAIL_CONTENT3);
        } catch (Exception e) {
            messageFeign.sendSimpleMail(MESSAGE_MAIL_TASK_TO, MESSAGE_MAIL_TITLE3, e.getMessage());
        }
    }
}
