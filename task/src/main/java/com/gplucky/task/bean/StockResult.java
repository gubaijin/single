package com.gplucky.task.bean;

import java.util.List;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class StockResult {
    /**
     * 总条数
     */
    private String totalCount;
    /**
     * 当前页
     */
    private String page;
    /**
     * 显示条数
     */
    private String num;
    private List<Stock> data;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<Stock> getData() {
        return data;
    }

    public void setData(List<Stock> data) {
        this.data = data;
    }
}
