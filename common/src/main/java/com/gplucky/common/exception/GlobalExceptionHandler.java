package com.gplucky.common.exception;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ehsy_it on 2017/1/23.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    protected static HttpHeaders headers = new HttpHeaders();
    static {
        headers.add("Content-Type", "application/json; charset=utf-8");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public <T> ResponseEntity<String> jsonErrorHandler(Exception exception) throws Exception {
        HttpResult<T> result = new HttpResult<T>();
        if(exception instanceof CMRuntimeException){
            CMRuntimeException cmRuntimeException = (CMRuntimeException) exception;
            result.setMark(cmRuntimeException.getCode());
            result.setMessage(cmRuntimeException.getMessage());
            LOG.error("平台异常[{}]", exception.getMessage());
        }else {
            result.setMark(ResultCode.CODE_COMMON_500.getCode());
            result.setMessage(ResultCode.CODE_COMMON_500.getMsg());
            LOG.error("系统异常[{}]", exception);
        }
        return new ResponseEntity<String>(JSON.toJSONString(result), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
