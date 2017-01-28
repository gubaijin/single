package com.gplucky.task.bean;

import com.gplucky.common.mybatis.model.Stock;

import java.util.List;
import java.util.Optional;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class StockResult{
    /**
     * 总条数
     */
    private Optional<String> totalCount;
    /**
     * 当前页
     */
    private Optional<String> page;
    /**
     * 显示条数
     */
    private Optional<String> num;
    private Optional<List<Stock>> data;

    public Optional<String> getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Optional<String> totalCount) {
        this.totalCount = totalCount;
    }

    public Optional<String> getPage() {
        return page;
    }

    public void setPage(Optional<String> page) {
        this.page = page;
    }

    public Optional<String> getNum() {
        return num;
    }

    public void setNum(Optional<String> num) {
        this.num = num;
    }

    public Optional<List<Stock>> getData() {
        return data;
    }

    public void setData(Optional<List<Stock>> data) {
        this.data = data;
    }
}
