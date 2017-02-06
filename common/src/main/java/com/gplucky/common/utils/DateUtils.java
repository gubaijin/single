package com.gplucky.common.utils;

import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.common.exception.ResultCode;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
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
        return localDate.atTime(0,0,0).format(DateTimeFormatter.ofPattern(FORMAT_STR_1));
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
        return localDate.atTime(23,59,59).format(DateTimeFormatter.ofPattern(FORMAT_STR_1));
    }

    /**
     * 下一个工作日
     * @param localDate
     * @return
     */
    public static LocalDate nextWorkingDay(LocalDate localDate){
        return localDate.with(new NextWorkingDay());
    }

    /**
     * 下N个工作日:0默认当前
     * @param n
     * @return
     */
    public static LocalDate nextWorkingDay(int n){
        LocalDate localDate = LocalDate.now();
        if(n > 0){
            for(int i=0; i < n; i++){
                localDate = localDate.with(new NextWorkingDay());
            }
        }
        return localDate;
    }

    /**
     * 上一个工作日
     * @param localDate
     * @return
     */
    public static LocalDate lastWorkingDay(LocalDate localDate){
        return localDate.with(new LastWorkingDay());
    }

    /**
     * 上N个工作日:0默认当前
     * @param n
     * @return
     */
    public static LocalDate lastWorkingDay(int n){
        LocalDate localDate = LocalDate.now();
        if(n > 0){
            for(int i=0; i < n; i++){
                localDate = localDate.with(new LastWorkingDay());
            }
        }
        return localDate;
    }

    static class NextWorkingDay implements TemporalAdjuster{


        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if(dow == DayOfWeek.FRIDAY){
                dayToAdd = 3;
            }else if( dow == DayOfWeek.SATURDAY){
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    static class LastWorkingDay implements TemporalAdjuster{

        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = -1;
            if(dow == DayOfWeek.MONDAY){
                dayToAdd = -3;
            }else if( dow == DayOfWeek.SUNDAY){
                dayToAdd = -2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }
}
