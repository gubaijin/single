package com.gplucky.common.bean;

/**
 * Created by ehsy_it on 2017/1/23.
 */
public class HttpResult<T> {
    private String mark;
    private String message;
    private T data;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
