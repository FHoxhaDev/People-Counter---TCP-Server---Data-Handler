package com.example.demo.tcp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Time zone utility class
 *
 * @author hyq
 * @date 2020/12/22
 */
public class TimeZoneUtils {

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public final static String TIME_ZONE = "Asia/Shanghai";

    /**
     * Retrieves the current time in the specified time zone
     *
     * @param date
     * @param timezone Asia/Shanghai
     * @return
     */
    public static Date getDate(Date date, String timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = sdf.parse(getDateString(date, timezone));
        } catch (ParseException e) {
            System.err.println("时间转换异常:  "+e);
        }
        return date;
    }

    /**
     * Gets the current time string for the specified time zone
     *
     * @param date
     * @param timezone Asia/Shanghai
     * @return
     */
    public static String getDateString(Date date, String timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone == null ? TIME_ZONE : timezone));
        return sdf.format(date);
    }

    /**
     * Gets the current time string for the specified time zone
     *
     * @param date
     * @param timezone Asia/Shanghai
     * @return
     */
    public static String getDateString(Date date, String timezone, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone == null ? TIME_ZONE : timezone));
        return sdf.format(date);
    }


}
