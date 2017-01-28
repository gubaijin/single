package com.gplucky.common.exception;

/**
 * Created by ehsy_it on 2017/1/23.
 */
public enum ResultCode {
    CODE_COMMON_500("500", "系统异常"),
    CODE_ERROR_DB("30000", "数据库操作异常");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}