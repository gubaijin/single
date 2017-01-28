package com.gplucky.common.transport.data;

import java.util.Optional;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class RespData <T> {
    private int statusCode;
    private Optional<T> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Optional<T> getData() {
        return data;
    }

    public void setData(Optional<T> data) {
        this.data = data;
    }
}
