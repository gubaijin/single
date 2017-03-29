package com.gplucky.common.bean;

/**
 * 连涨连跌
 * Created by ehsy_it on 2017/3/28.
 */
public class UpAndDown {
    /**
     * 筛选条件：1：创业板；2：次新股；3：去重
     */
    public static final String PARAM_GROWTH = "1";
    /**
     * 筛选条件：1：创业板；2：次新股；3：去重
     */
    public static final String PARAM_SUBNEW = "2";
    /**
     * 筛选条件：1：创业板；2：次新股；3：去重
     */
    public static final String PARAM_DISTINCT = "3";

    /**
     * 涨：1；跌：2
     */
    public static final String TYPE_UP = "1";
    /**
     * 涨：1；跌：2
     */
    public static final String TYPE_DOWN = "2";

    private String type;

    private int num;

    private String[] params = {};

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
