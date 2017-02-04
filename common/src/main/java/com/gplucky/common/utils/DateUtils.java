package com.gplucky.common.utils;

import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.common.exception.ResultCode;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by ehsy_it on 2017/1/29.
 */
public class DateUtils {
    
    public static String FORMAT_STR_1 = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 日期格式为：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateByFormat1(String dateStr){
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_STR_1);
            if(StringUtils.isEmpty(dateStr)){
                return new Date();
            }else{
                return dateFormat.parse(dateStr);
            }
        } catch (ParseException e) {
            throw new CMRuntimeException(ResultCode.CODE_ERROR_UTILS_DATE.getCode(), ResultCode.CODE_ERROR_UTILS_DATE.getMsg());
        }
    }

    /**
     * 获得日期的开始：Sat Feb 04 00:00:00 CST 2017
     * @param localDate
     * @return
     */
    public static Date getDateStart(LocalDate localDate){
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获得日期的开始：2017-02-04 00:00:00
     * @param localDate
     * @return
     */
    public static String getDateStartFormat(LocalDate localDate){
        return new SimpleDateFormat(FORMAT_STR_1).format(getDateStart(localDate));
    }

    /**
     * 获得日期的结束：Sat Feb 04 23:59:59 CST 2017
     * @param localDate
     * @return
     */
    public static Date getDateEnd(LocalDate localDate){
        LocalDateTime localDateTime = localDate.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获得日期的结束：2017-02-04 23:59:59
     * @param localDate
     * @return
     */
    public static String getDateEndFormat(LocalDate localDate){
        return new SimpleDateFormat(FORMAT_STR_1).format(getDateEnd(localDate));
    }
}
