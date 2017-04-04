package com.gplucky.common.bean;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.springframework.util.StringUtils;

/**
 * Created by ehsy_it on 2017/2/23.
 */
public class PageG {
    private int pageSize = 15;

    private int totalPage;

    private int count;

    private int pageNo = 1;

    public static PageG convert(String page){
        if (StringUtils.isEmpty(page)) {
            return new PageG();
        } else {
            return JSONObject.parseObject(page, PageG.class);
        }
    }

    public PageG setCountAndTotalPage(PageG pageG, Page pageHelper){
        if(null == pageG){
            pageG = new PageG();
        }
        int count = (int) pageHelper.getTotal();
        int pageSize = pageG.getPageSize();
        pageG.setCount(count);
        pageG.setTotalPage(count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1);
        return pageG;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
