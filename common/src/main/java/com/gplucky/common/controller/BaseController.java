package com.gplucky.common.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.bean.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by ehsy_it on 2017/1/23.
 */
public class BaseController {
    protected static HttpHeaders headers = new HttpHeaders();

    public static final String SUCCESS_MSG = "success";
    public static final String FAILED_MSG = "failed";
    public static final String SUCCESS_MARK = "1";
    public static final String FAILED_MARK = "0";

    static {
        headers.add("Content-Type", "application/json; charset=utf-8");
    }

    protected ResponseEntity<String> returnSuccessMsg(){
        HttpResult<String> result = new HttpResult();
        result.setMark(SUCCESS_MARK);
        result.setMessage(SUCCESS_MSG);
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.OK);
    }

    protected <T> ResponseEntity<String> returnSuccessMsg(T t){
        return returnMsg(SUCCESS_MARK, SUCCESS_MSG, null, t);
    }

    protected <T> ResponseEntity<String> returnSuccessMsg(Page page, T t){
        return returnMsg(SUCCESS_MARK, SUCCESS_MSG, page, t);
    }

    protected <T> ResponseEntity<String> returnFailMsg(String msg){
        return this.returnFailMsg(msg, null);
    }

    protected <T> ResponseEntity<String> returnFailMsg(String msg, T t){
        return returnMsg(FAILED_MARK, msg, null, t);
    }

    private <T> ResponseEntity<String> returnMsg(String mark, String msg, Page page, T t) {
        HttpResult<T> result = new HttpResult<T>();
        result.setMark(mark);
        result.setMessage(msg);
        result.setPage(page);
        result.setData(t);
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.OK);
    }
}
