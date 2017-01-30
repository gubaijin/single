package com.gplucky.task.service.impl;

import com.google.common.collect.Maps;
import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.common.exception.ResultCode;
import com.gplucky.common.mybatis.dao.StockMapper;
import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.StockExample;
import com.gplucky.common.mybatis.model.StockHistory;
import com.gplucky.common.transport.RequestUtil;
import com.gplucky.common.transport.data.RespData;
import com.gplucky.common.utils.DateUtils;
import com.gplucky.task.bean.ErrorCode;
import com.gplucky.task.bean.StockResp;
import com.gplucky.task.service.StockHistoryService;
import com.gplucky.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ehsy_it on 2017/1/26.
 */
@Service
public class StockServiceImpl implements StockService{
    private static final Logger LOG = LoggerFactory.getLogger(StockServiceImpl.class);
    AtomicInteger page;

    @Value("${juhe.stock.appkey}")
    private String APP_KEY;

    @Value("${juhe.url.stock.list.sh}")
    private String STOCK_URL_LIST_SH;

    @Value("${juhe.url.stock.list.sz}")
    private String STOCK_URL_LIST_SZ;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockHistoryService stockHistoryService;

    @Override
    public boolean fetchStockInfo() {
        /**
         * 先同步上海股市信息
         * 再同步深圳股市信息
         */
        page = new AtomicInteger(1);
        judgeLoopAndUpdateInfo(STOCK_URL_LIST_SH);
        return true;
    }

    @Override
    public boolean initSHList() {
        page = new AtomicInteger(1);
        judgeLoopAndRecord(STOCK_URL_LIST_SH);
        return true;
    }

    @Override
    public boolean initSZList() {
        page = new AtomicInteger(1);
        judgeLoopAndRecord(STOCK_URL_LIST_SZ);
        return true;
    }

    private void judgeLoopAndUpdateInfo(String url) {
        Optional<StockResp> resp = sendStockRequest(url, page.getAndIncrement());
        resp.ifPresent(stockResp -> {
            //写入股票表
            updateStock(stockResp);
            if(ErrorCode.EMPTY_DATA.getCode() != stockResp.getError_code()){
                judgeLoopAndRecord(url);
            }
            if((ErrorCode.EMPTY_DATA.getCode() == stockResp.getError_code())
                    && !STOCK_URL_LIST_SZ.equals(url)){
                page = new AtomicInteger(1);
                judgeLoopAndRecord(STOCK_URL_LIST_SZ);
            }
        });
    }

    /**
     * 初始化时使用
     * @param url
     */
    private void judgeLoopAndRecord(String url) {
        Optional<StockResp> resp = sendStockRequest(url, page.getAndIncrement());
        resp.ifPresent(stockResp -> {
            //写入股票表
            insertStock(stockResp);
            if(ErrorCode.EMPTY_DATA.getCode() != stockResp.getError_code()){
                judgeLoopAndRecord(url);
            }
        });
    }

    private void updateStock(StockResp stockResp) {
        stockResp.getResult().ifPresent(stockResult -> {
            LOG.info(stockResult.getTotalCount().get() + "|" +
                    stockResult.getPage().get() + "|" +
                    stockResult.getNum().get());
            stockResult.getData().ifPresent(stockList->{
                stockList.stream().forEach(stock -> {
                    try {
                        updateStockAndInsertHistory(stock);
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                        throw new CMRuntimeException(ResultCode.CODE_ERROR_DB.getCode(),
                                ResultCode.CODE_ERROR_DB.getMsg() + "updateStock;");
                    }
                });
            });
        });
    }

    /**
     * 初始化时：写入股票表
     * @param stockResp
     */
    private void insertStock(StockResp stockResp) {
        stockResp.getResult().ifPresent(stockResult -> {
            LOG.info(stockResult.getTotalCount().get() + "|" +
                    stockResult.getPage().get() + "|" +
                    stockResult.getNum().get());
            stockResult.getData().ifPresent(stockList->{
                stockList.stream().forEach(stock -> {
                    try {
                        insertStockAndInsertHistory(stock);
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                        throw new CMRuntimeException(ResultCode.CODE_ERROR_DB.getCode(),
                                ResultCode.CODE_ERROR_DB.getMsg() + "insertStock;");
                    }
                });
            });
        });
    }

    private void updateStockAndInsertHistory(Stock stock) {
        Date date = new Date();
        stock.setUpdateTime(date);
        StockExample example = new StockExample();
        example.createCriteria().andCodeEqualTo(stock.getCode());
        stockMapper.updateByExampleSelective(stock, example);

        StockHistory stockHistory = new StockHistory();
        BeanUtils.copyProperties(stock, stockHistory);
        stockHistory.setCreateTime(date);
        stockHistory.setUpdateTime(date);
        stockHistoryService.insert(stockHistory);
    }

    /**
     * 仅init初始化时
     * @param stock
     */
    private void insertStockAndInsertHistory(Stock stock) {
        Date date = new Date();
        stock.setCreateTime(date);
        stock.setUpdateTime(date);
        stockMapper.insertSelective(stock);

        StockHistory stockHistory = new StockHistory();
        BeanUtils.copyProperties(stock, stockHistory);
        stockHistory.setCreateTime(DateUtils.getDateByFormat1("2017-01-26 15:00:00"));
        stockHistoryService.insert(stockHistory);
    }

    private Optional<StockResp> sendStockRequest(String url, int page) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("key", APP_KEY);
        params.put("page", String.valueOf(page));
        RespData<StockResp> respData = RequestUtil.createDefault().get(url, params, StockResp.class);
        return respData.getData();
    }

}
