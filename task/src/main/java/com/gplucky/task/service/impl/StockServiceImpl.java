package com.gplucky.task.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.common.exception.ResultCode;
import com.gplucky.common.mybatis.dao.StockMapper;
import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.StockExample;
import com.gplucky.common.mybatis.model.StockHistory;
import com.gplucky.common.mybatis.model.StockNew;
import com.gplucky.common.transport.RequestUtil;
import com.gplucky.common.transport.data.RespData;
import com.gplucky.common.utils.DateUtils;
import com.gplucky.common.utils.RedisUtils;
import com.gplucky.task.bean.ErrorCode;
import com.gplucky.task.bean.StockResp;
import com.gplucky.task.service.StockHistoryService;
import com.gplucky.task.service.StockNewService;
import com.gplucky.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Created by ehsy_it on 2017/1/26.
 */
@Service
public class StockServiceImpl implements StockService {
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

    @Autowired
    private StockNewService stockNewService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<Stock> getStockList() {
        return stockMapper.selectByExample(null);
    }

    /**
     * 先同步上海股市信息
     * 再同步深圳股市信息
     */
    @Override
    public boolean fetchStockInfo() {
        //判断是否有新数据需要同步
        boolean flg = isNeedFetch();
        if (flg) {
            page = new AtomicInteger(1);
            judgeLoopAndUpdateInfo(STOCK_URL_LIST_SH);
            page = new AtomicInteger(1);
            judgeLoopAndUpdateInfo(STOCK_URL_LIST_SZ);
        } else {
            LOG.info("没有最新数据，无需更新！");
        }
        return true;
    }

    /**
     * 请求20条数据进行对比，都相等则表示没有最新数据
     * 成交量大于0的情况下，昨收、今开、成交量、成交额、涨跌幅、涨跌额都相等则判断为没有最新数据
     *
     * @return
     */
    private boolean isNeedFetch() {
        List<Boolean> isNeedFetchFlgs = Lists.newArrayList();
        Optional<StockResp> resp = sendStockRequest(STOCK_URL_LIST_SH, 1);
        resp.ifPresent(stockResp -> {
            stockResp.getResult().ifPresent(stockResult -> {
                stockResult.getData().ifPresent(stockList -> {
                    stockList.stream().forEach(stockNew -> {
                        Stock stock = getStockByCode(stockNew.getCode());
                        boolean stockEqualsFlg = checkStockEquals(stockNew, stock);
                        isNeedFetchFlgs.add(!stockEqualsFlg);
                    });
                });
            });
        });
        LOG.info("是否需要数据抓取(isNeedFetchFlgs.contains(true))=" + isNeedFetchFlgs.contains(true));
        return isNeedFetchFlgs.contains(true);
    }

    /**
     * 成交量大于0的情况下，昨收、今开、成交量、成交额、涨跌幅、涨跌额都相等则判断为没有最新数据
     *
     * @param stockNew
     * @param stock
     * @return
     */
    private boolean checkStockEquals(Stock stockNew, Stock stock) {
        LOG.info("stockNew------>" + stockNew.toString());
        LOG.info("stock------>" + stock.toString());
        if (stockNew.getSettlement().equals(stock.getSettlement())
                && stockNew.getOpen().equals(stock.getOpen())
                && stockNew.getVolume().equals(stock.getVolume())
                && stockNew.getAmount().equals(stock.getAmount())
                && stockNew.getChangepercent().equals(stock.getChangepercent())
                && stockNew.getPricechange().equals(stock.getPricechange())) {
            return true;
        } else {
            return false;
        }
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

    /**
     * 失败补偿同步股列表
     *
     * @return
     */
    @Override
    public boolean fetchCompensation() {
        page = new AtomicInteger(1);
        fetchStockInfoCompensation(STOCK_URL_LIST_SH);
        page = new AtomicInteger(1);
        fetchStockInfoCompensation(STOCK_URL_LIST_SZ);
        return true;
    }

    /**
     * 查询股票信息
     * @param stock
     * @return
     */
    @Override
    public List<Stock> select(Stock stock) {
        return stockMapper.selectByExample(convertExample(stock));
    }

    private StockExample convertExample(Stock stock) {
        StockExample example = null;
        if(null != stock){
            example = new StockExample();
            String code = stock.getCode();
            String symbol = stock.getSymbol();
            String name = stock.getName();
            Integer score = stock.getScore();
            StockExample.Criteria criteria = example.createCriteria();
            if(!StringUtils.isEmpty(code)){
                criteria.andCodeLike("%" + code + "%");
            }
            if(!StringUtils.isEmpty(symbol)){
                criteria.andSymbolLike("%" + symbol + "%");
            }
            if(!StringUtils.isEmpty(name)){
                criteria.andNameLike("%" + name + "%");
            }
            if(null != score && score > 0){
                criteria.andScoreGreaterThanOrEqualTo(score);
            }
            example.setOrderByClause("changepercent desc, volume desc, amount desc");
        }
        return example;
    }

    private void fetchStockInfoCompensation(String url) {
        Optional<StockResp> resp = sendStockRequest(url, page.getAndIncrement());
        resp.ifPresent(stockResp -> {
            //写入股票表（先判断是否存在）
            stockOperation(stockResp, stock -> updateStockAndHistoryAfterChecked(stock));
            checkJudgeLoop(url, stockResp);
        });
    }

    /**
     * 写入股票表及历史表（先判断是否存在）
     * @param stock
     */
    private void updateStockAndHistoryAfterChecked(Stock stock) {
        //检查stock表是否存在当天数据
        boolean stockIsExist = checkStockIsExistByCodeNowDate(stock);
        if(!stockIsExist){
            //不存在，需要更新
            updateStockOrInsert(stock);
        }
        //检查stock_history中是否存在当天数据
        boolean stockHistoryIsExist = checkStockHistoryIsExistByCodeNowDate(stock);
        if(!stockHistoryIsExist){
            //不存在，需要获取
            insertStockHistory(stock);
        }
    }

    /**
     * 检查stock_history中是否存在当天数据
     * @param stock
     * @return
     */
    private boolean checkStockHistoryIsExistByCodeNowDate(Stock stock) {
        StockExample example = new StockExample();
        LocalDate localDate = LocalDate.now();
        example.createCriteria().andCodeEqualTo(stock.getCode())
                .andCreateTimeBetween(DateUtils.getDateStart(localDate), DateUtils.getDateEnd(localDate));
        int count = stockMapper.countByExample(example);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 检查stock表是否存在当天数据
     * @param stock
     * @return
     */
    private boolean checkStockIsExistByCodeNowDate(Stock stock) {
        LocalDate localDate = LocalDate.now();
        return stockHistoryService.isExistByCodeNowDate(stock.getCode(), localDate);
    }

    private void checkJudgeLoop(String url, StockResp stockResp) {
        //持续请求
        if (ErrorCode.EMPTY_DATA.getCode() != stockResp.getError_code()) {
            judgeLoopAndRecord(url);
        }
    }

    private void checkJudgeLoopAndUpdateInfo(String url, StockResp stockResp) {
        //持续请求
        if (ErrorCode.EMPTY_DATA.getCode() != stockResp.getError_code()) {
            judgeLoopAndUpdateInfo(url);
        }
    }

    private void judgeLoopAndUpdateInfo(String url) {
        Optional<StockResp> resp = sendStockRequest(url, page.getAndIncrement());
        resp.ifPresent(stockResp -> {
            //写入股票表
            stockOperation(stockResp, stock -> updateStockAndInsertHistory(stock));
            checkJudgeLoopAndUpdateInfo(url, stockResp);
        });
    }

    /**
     * 初始化时使用
     * r
     * @param url
     */
    private void judgeLoopAndRecord(String url) {
        Optional<StockResp> resp = sendStockRequest(url, page.getAndIncrement());
        resp.ifPresent(stockResp -> {
            //写入股票表
            stockOperation(stockResp, stock -> insertStockAndInsertHistory(stock));
            if (ErrorCode.EMPTY_DATA.getCode() != stockResp.getError_code()) {
                judgeLoopAndRecord(url);
            }
        });
    }

    private void stockOperation(StockResp stockResp, Consumer<Stock> c) {
        stockResp.getResult().ifPresent(stockResult -> {
            LOG.info(stockResult.getTotalCount().get() + "|" +
                    stockResult.getPage().get() + "|" +
                    stockResult.getNum().get());
            stockResult.getData().ifPresent(stockList -> {
                stockList.stream().forEach(stock -> {
                    try {
                        c.accept(stock);
//                        updateStockAndInsertHistory(stock);
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                        throw new CMRuntimeException(ResultCode.CODE_ERROR_DB.getCode(),
                                ResultCode.CODE_ERROR_DB.getMsg() + "stockOperation;");
                    }
                });
            });
        });
    }

    /**
     * 初始化时：写入股票表
     *
     * @param stockResp
     */
/*    private void insertStock(StockResp stockResp) {
        stockResp.getResult().ifPresent(stockResult -> {
            LOG.info(stockResult.getTotalCount().get() + "|" +
                    stockResult.getPage().get() + "|" +
                    stockResult.getNum().get());
            stockResult.getData().ifPresent(stockList -> {
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
    }*/

    /**
     * 更新最新数据、记录历史数据、新股创建数据、新股记录进redis
     *
     * @param stock
     */
    private void updateStockAndInsertHistory(Stock stock) {
        updateStockOrInsert(stock);

        insertStockHistory(stock);
    }

    private void insertStockHistory(Stock stock) {
        Date date = new Date();
        StockHistory stockHistory = new StockHistory();
        BeanUtils.copyProperties(stock, stockHistory);
        stockHistory.setCreateTime(date);
        stockHistory.setUpdateTime(date);
        stockHistoryService.insert(stockHistory);
    }

    private void updateStockOrInsert(Stock stock) {
        Date date = new Date();
        stock.setUpdateTime(date);
        StockExample example = new StockExample();
        example.createCriteria().andCodeEqualTo(stock.getCode());
        int updateFlg = stockMapper.updateByExampleSelective(stock, example);
        if (!(updateFlg > 0)) {
            stock.setCreateTime(date);
            stockMapper.insertSelective(stock);
            //记录新股
            insertStockNew(stock);
        }
    }

    /**
     * 记录新股
     * @param stock
     */
    public void insertStockNew(Stock stock) {
        Date date = new Date();
        StockNew stockNew = new StockNew();
        BeanUtils.copyProperties(stock, stockNew);
        stockNew.setCreateTime(date);
        stockNew.setUpdateTime(date);
        stockNewService.insert(stockNew);
    }

    public Stock getStockByCode(String code) {
        List<Stock> list;
        try {
            StockExample example = new StockExample();
            example.createCriteria().andCodeEqualTo(code);
            list = stockMapper.selectByExample(example);
        } catch (Exception e) {
            throw new CMRuntimeException(ResultCode.CODE_ERROR_DB.getCode(),
                    ResultCode.CODE_ERROR_DB.getMsg() + "getStockByCode() code=" + code);
        }
        if (list.size() > 1) {
            throw new CMRuntimeException(ResultCode.CODE_ERROR_DB_1.getCode(),
                    ResultCode.CODE_ERROR_DB_1.getMsg() + "getStockByCode() code=" + code);
        }
        if (list.size() == 0) {
            throw new CMRuntimeException(ResultCode.CODE_ERROR_STOCK.getCode(),
                    ResultCode.CODE_ERROR_STOCK.getMsg() + "getStockByCode() code=" + code);
        }
        return list.get(0);
    }

    /**
     * 仅init初始化时
     *
     * @param stock
     */
    private void insertStockAndInsertHistory(Stock stock) {
        Date date = new Date();
        stock.setCreateTime(date);
        stock.setUpdateTime(date);
        stockMapper.insertSelective(stock);

        StockHistory stockHistory = new StockHistory();
        BeanUtils.copyProperties(stock, stockHistory);
        stockHistory.setCreateTime(DateUtils.getDateByFormat1("2017-02-03 15:00:00"));
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
