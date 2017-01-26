package com.gplucky.task.bean;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class Stock {
    /**
     * 代码
     */
    private String symbol;
    /**
     * 名称
     */
    private String name;
    /**
     * 最新价
     */
    private String trade;
    /**
     * 涨跌额
     */
    private String pricechange;
    /**
     * 涨跌幅
     */
    private String changepercent;
    /**
     * 买入
     */
    private String buy;
    /**
     * 卖出
     */
    private String sell;
    /**
     * 昨收
     */
    private String settlement;
    /**
     * 今开
     */
    private String open;
    /**
     * 最高
     */
    private String high;
    /**
     * 最低
     */
    private String low;
    /**
     * 成交量
     */
    private String volume;
    /**
     * 成交额
     */
    private String amount;
    /**
     * 简码
     */
    private String code;
    /**
     * 时间
     */
    private String ticktime;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPricechange() {
        return pricechange;
    }

    public void setPricechange(String pricechange) {
        this.pricechange = pricechange;
    }

    public String getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(String changepercent) {
        this.changepercent = changepercent;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTicktime() {
        return ticktime;
    }

    public void setTicktime(String ticktime) {
        this.ticktime = ticktime;
    }
}
