package com.gplucky.common.utils;

import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.common.exception.ResultCode;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ehsy_it on 2017/1/29.
 */
public class DateUtils {
    /**
     * 日期格式为：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateByFormat1(String dateStr){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.isEmpty(dateStr)){
                return new Date();
            }else{
                return dateFormat.parse(dateStr);
            }
        } catch (ParseException e) {
            throw new CMRuntimeException(ResultCode.CODE_ERROR_UTILS_DATE.getCode(), ResultCode.CODE_ERROR_UTILS_DATE.getMsg());
        }
    }
}
