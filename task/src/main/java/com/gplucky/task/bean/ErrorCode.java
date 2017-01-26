package com.gplucky.task.bean;

/**
 * Created by ehsy_it on 2017/1/26.
 */
public enum ErrorCode {
    ERROR_PARAMS("202101", "参数错误"),
    EMPTY_DATA("202102", "查询不到结果"),
    ERROR_NETWORK("202103", "网络异常");

    private String code;
    private String msg;

    ErrorCode(String code, String msg){
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
