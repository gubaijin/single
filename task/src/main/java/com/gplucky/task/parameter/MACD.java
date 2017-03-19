package com.gplucky.task.parameter;

/**
 * Created by ehsy_it on 2017/3/15.
 */

import com.gplucky.common.mybatis.model.StockHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 平滑异同移动平均线指标
 *
 */
public class MACD {
/*
    public HashMap<String, Double> getMACD(final List<StockHistory> list){
        HashMap<String, Double> macdData = new HashMap<String, Double>();
        Double di = getDI(list.get(0));

        Double L12 = 0.1538;
        Double L26 = 0.0741;

        Double EMA12 = L12 * (di - );
    }

    private Double getEMA12*/

    /**
     * 得到加权平均指数(DI)
     * @param stock
     * @return
     */
    private Double getDI( StockHistory stock ) {
        //当日最高
        Double high = Double.valueOf(stock.getHigh());
        //当日最低
        Double low = Double.valueOf(stock.getLow());
        //当日收盘
        Double trade = Double.valueOf(stock.getTrade());

        //加权平均指数(DI)
        return ( high + low + 2 * trade ) / 4;
    }

    /**
     * Calculate EMA,
     *
     * @param list
     *            :Price list to calculate，the first at head, the last at tail.
     * @return
     */
    public static final Double getEXPMA(final List<Double> list, final int number) {
        // 开始计算EMA值，
        Double k = 2.0 / (number + 1.0);// 计算出序数
        Double ema = list.get(0);// 第一天ema等于当天收盘价
        for (int i = 1; i < list.size(); i++) {
            // 第二天以后，当天收盘 收盘价乘以系数再加上昨天EMA乘以系数-1
            ema = list.get(i) * k + ema * (1 - k);
        }
        return ema;
    }

    /**
     * calculate MACD values
     *
     * @param list
     *            :Price list to calculate，the first at head, the last at tail.
     * @param shortPeriod
     *            :the short period value.
     * @param longPeriod
     *            :the long period value.
     * @param midPeriod
     *            :the mid period value.
     * @return
     */
    public static final HashMap<String, Double> getMACD(final List<Double> list, final int shortPeriod, final int longPeriod, int midPeriod) {
        HashMap<String, Double> macdData = new HashMap<String, Double>();
        List<Double> diffList = new ArrayList<Double>();
        Double shortEMA = 0.0;
        Double longEMA = 0.0;
        Double dif = 0.0;
        Double dea = 0.0;

        for (int i = list.size() - 1; i >= 0; i--) {
            List<Double> sublist = list.subList(0, list.size() - i);
            shortEMA = MACD.getEXPMA(sublist, shortPeriod);
            longEMA = MACD.getEXPMA(sublist, longPeriod);
            dif = shortEMA - longEMA;
            diffList.add(dif);
        }
        dea = MACD.getEXPMA(diffList, midPeriod);
        macdData.put("DIF", dif);
        macdData.put("DEA", dea);
        macdData.put("MACD", (dif - dea) * 2);
        return macdData;
    }
}
