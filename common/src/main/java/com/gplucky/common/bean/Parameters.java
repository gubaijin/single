package com.gplucky.common.bean;

/**
 * Created by ehsy_it on 2017/2/27.
 */

/**
 * 股票参数类
 */
public class Parameters {
    /**
     * 创业板
     */
    private boolean growth;
    /**
     * 次新股
     */
    private boolean subNew;

    public boolean isSubNew() {
        return subNew;
    }

    public void setSubNew(boolean subNew) {
        this.subNew = subNew;
    }

    public boolean isGrowth() {
        return growth;
    }

    public void setGrowth(boolean growth) {
        this.growth = growth;
    }
}
