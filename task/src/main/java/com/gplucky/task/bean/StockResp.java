package com.gplucky.task.bean;

import java.util.Optional;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class StockResp extends BaseResp{
    private Optional<StockResult> result;

    public Optional<StockResult> getResult() {
        return result;
    }

    public void setResult(Optional<StockResult> result) {
        this.result = result;
    }
}
