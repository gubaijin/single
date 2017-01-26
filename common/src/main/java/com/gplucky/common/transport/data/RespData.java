package com.gplucky.common.transport.data;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public class RespData <T> {
    private int statusCode;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
