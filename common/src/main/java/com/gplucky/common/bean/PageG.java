package com.gplucky.common.bean;

/**
 * Created by ehsy_it on 2017/2/23.
 */
public class PageG {
    private int pageSize = 15;

    private int total;

    private int pageNo = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
