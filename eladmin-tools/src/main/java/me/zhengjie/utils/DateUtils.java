package me.zhengjie.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.vvxx.exception.CustomFiveHundredErrorException;

public class DateUtils {

    // 时间格式(yyyy-MM-dd)
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    // 时间格式(yyyy_MM_dd)
    public final static String DATE_PATTERN_FOR_UNDERLINE = "yyyy_MM_dd";

    // 时间格式(yyyy-MM-dd HH:mm:ss)
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    // 时间格式(yyyy-MM-dd HH:mm:ss)
    public final static String DATE_ONLY_TIME_PATTERN_ = "HH:mm:ss";

    // 时间格式(yyyyMMddHHmmss)
    public final static String DATE_FILE_PATTERN = "yyyyMMddHHmmss";

    // 时间格式(yyyyMMddHHmmss)
    public final static String DATE_FOR_NUMBER_GANERATE = "yyyyMMddHHmmss-SSS";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatToUnderline(Date date) {
        return format(date, DATE_PATTERN_FOR_UNDERLINE);
    }

    public static String formatToLongString(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    public static Time getTimeByStingDate(String date) {
        String str = date;
        SimpleDateFormat format = new SimpleDateFormat(DATE_ONLY_TIME_PATTERN_);
        Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Time time = new Time(d.getTime());
        return time.valueOf(str);
    }

    /**
     * 获取当前时间指定前进或后退
     *
     * @param dayNumber 整数往后增加天数，负数往前回退天数
     * @param format    指定返回格式
     * @return
     */
    public static String getDateToDay(String date, int dayNumber, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date2 = null;
        try {
            date2 = StringToLongDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "转换错误";
        }
        Calendar c = Calendar.getInstance(); // 获取东八区时间
        c.setTime(date2);
        c.add(Calendar.DATE, dayNumber);
        return df.format(c.getTime());
    }

    /**
     * 获取当前时间指定前进或后退
     *
     * @param dayNumber 整数往后增加天数，负数往前回退天数
     * @param format    指定返回格式
     * @return
     */
    public static String getDateToDay1(String date, int dayNumber, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date2 = null;
        try {
            date2 = StringToLongDate1(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "转换错误";
        }
        Calendar c = Calendar.getInstance(); // 获取东八区时间
        c.setTime(date2);
        c.add(Calendar.DATE, dayNumber);
        return df.format(c.getTime());
    }

    /**
     * 获取当前时间指定前进或后退
     *
     * @param dayNumber 整数往后增加天数，负数往前回退天数
     * @param format    指定返回格式
     * @return
     */
    public static String getDateToDay(int dayNumber, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // 获取东八区时间
        c.add(Calendar.DATE, dayNumber);
        return df.format(c.getTime());
    }

    /**
     * 注册时间转换器
     */
    public static void dateConvert() {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                if (value == null || value.equals("")) {
                    return null;
                }
                String formatStyle = "";

//				Class<? extends Object> class1 = value.getClass();
                if (value.toString().length() == 10) {
                    formatStyle = DateUtils.DATE_PATTERN;
                } else {
                    formatStyle = DateUtils.DATE_TIME_PATTERN;
                }
                SimpleDateFormat format = new SimpleDateFormat(formatStyle);
                Date parse = null;
                try {
                    parse = format.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException("日期转化异常");
//					throw new CustomFiveHundredErrorException("日期转化异常");
                }
                return parse;
            }
        }, Date.class);
    }

    /**
     * /** 时间(yyyy-MM-dd HH:mm:ss格式)
     *
     * @Date 2016-06-05
     */
    public static String date() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.format(new Date());
    }

    public static String date_ymd(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static String date_ymd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String date_end() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + "23:59:59");
        return sdf.format(new Date());
    }

    /**
     * 时间(yyyy-MM-dd HH:mm:ss格式)
     *
     * @throws ParseException
     * @Date 2016-06-05
     */
    public static Date date_ymd(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.parse(date);
    }

    /**
     * 时间(字符串转时间格式)
     *
     * @Date 2016-06-05
     */
    public static Date StringNewDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = sdf.parse(str);
        return date;
    }

    /**
     * 时间(字符串转时间格式)
     *
     * @Date 2016-06-05
     */
    public static Date StringToLongDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        Date date = sdf.parse(str);
        return date;
    }

    /**
     * 时间(字符串转时间格式)
     *
     * @Date 2016-06-05
     */
    public static Date StringToLongDate1(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date date = sdf.parse(str);
        return date;
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 时间差(月，以及天)
     *
     * @throws ParseException
     * @Date 2016-06-05
     */
    public static String monthAndday(String startTime, String endTime) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date startdate = sdf.parse(startTime);
        Date enddate = sdf.parse(endTime);

        Calendar calstart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        calEnd.setTime(enddate);
        calstart.setTime(startdate);

        int yearNow = calEnd.get(Calendar.YEAR);
        int monthNow = calEnd.get(Calendar.MONTH);
        int dayOfMonthNow = calEnd.get(Calendar.DAY_OF_MONTH);

        calstart.setTime(startdate);
        int yearBirth = calstart.get(Calendar.YEAR);
        int monthBirth = calstart.get(Calendar.MONTH);
        int dayOfMonthBirth = calstart.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        int month = 0;
        int day = 0;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;

                } else {
                    age--;
                    month = monthNow + 12 - monthBirth;
                    if (dayOfMonthNow <= dayOfMonthBirth) {
                        day = dayOfMonthNow + 30 - dayOfMonthBirth;
                    } else {
                        day = dayOfMonthNow - dayOfMonthBirth;
                    }
                }
            }
        } else {
            month = monthNow - monthBirth;

            if (dayOfMonthNow <= dayOfMonthBirth) {
                day = dayOfMonthNow + 30 - dayOfMonthBirth;
            } else {
                day = dayOfMonthNow - dayOfMonthBirth;
            }

        }

        String Duration = age * 12 + month + "个月" + day + "天";

        return Duration;

    }

    /**
     * 时间(某个时间增加几个月的的时间)
     */
    public static String AddDuration(String startTime, int Duration) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate = sdf.parse(startTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startdate);

        calendar.add(Calendar.MONTH, Duration);
        String date = sdf.format(calendar.getTime());

        return date;

    }

    public static Date date_ymd_hms(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.parse(date);
    }

    public static String timeInMillisToString(long number) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(number);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取时间作为文件名后缀
     *
     * @return
     * @throws ParseException
     */
    public static String date_file() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FILE_PATTERN);
        return sdf.format(new Date());
    }

    public static String date_ymd_hms(long timeSta) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        return sdf.format(new Date(timeSta));
    }

    public static void main(String[] args) {
        String dateToDay = DateUtils.getDateToDay("2020-06-19 23:59:59",
                -1, DateUtils.DATE_PATTERN);
    }

    /**
     * 判断是否为周末
     *
     * @author witt
     * @description
     * @date 13:42 2020/8/5
     */
    public static boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat(DATE_PATTERN);
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为周末
     *
     * @author witt
     * @description
     * @date 13:42 2020/8/5
     */
    public static boolean isWeekend(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是星期天
     *
     * @author witt
     * @description
     * @date 10:20 2020/8/7
     */
    public static boolean isSunDay() {
        Map<String, String> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一，这里我们设置为周日
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            return true;
        }
        return false;
    }

}
