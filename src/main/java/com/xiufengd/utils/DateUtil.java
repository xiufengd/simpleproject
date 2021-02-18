package com.xiufengd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 获取时间的字符串
     * @param date
     * @return
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    
    /**
     * 获取时间的字符串
     * @param date
     * @param format
     * @return
     */
    public static String getStringDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }
    /**
     * 根据yyyy-MM-dd格式字符串获取时间
     * @param date
     * @return
     */
    public static Date getDateByString(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateString = formatter.parse(date);
        return dateString;
    }

    /**
     * 根据format格式字符串获取时间
     * @param date
     * @param format
     * @return
     */
    public static Date getDateByString(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateString = formatter.parse(date);
        return dateString;
    }
    /**
     * 时间格式转换
     * 2020-04-03  2020年4月3日
     * @param date
     * @return
     */
    public static String getStringTime(String date) {
        String time = date.substring(0,4) + "年" +  date.substring(5,7)+"月" + date.substring(8,10) +"日";
        return time;
    }

    /**
     * 在当前日期值上增减分钟
     * @param num
     * @return
     */
    public static Date addMinute(int num) {
        return addMinute(new Date(), num);
    }

    /**
     * 在日期值上增减分钟
     * @param val
     * @param num
     * @return
     */
    public static Date addMinute(Date val, int num) {
        return calendarAdd(val, Calendar.MINUTE, num);
    }

    /**
     * 在当前日期值上增减小时
     * @param num
     * @return
     */
    public static Date addHour(int num) {
        return addHour(new Date(), num);
    }

    /**
     * 在日期值上增减小时
     * @param val
     * @param num
     * @return
     */
    public static Date addHour(Date val, int num) {
        return calendarAdd(val, Calendar.HOUR, num);
    }

    /**
     * 在当前日期值上增减天数
     * @param num
     * @return
     */
    public static Date addDay(int num) {
        return addDay(new Date(), num);
    }

    /**
     * 在日期值上增减天数
     * @param val
     * @param num
     * @return
     */
    public static Date addDay(Date val, int num) {
        return calendarAdd(val, Calendar.DATE, num);
    }

    /**
     * 在当前日期值上增减周数
     * @param num
     * @return
     */
    public static Date addWeek(int num){
        return addWeek(new Date(),num);
    }

    /**
     * 在当前日期值上增减周数
     * @param val
     * @param num
     * @return
     */
    public static Date addWeek(Date val,int num){
        return calendarAdd(val, Calendar.WEEK_OF_YEAR,num);
    }

    /**
     * 在当前日期值上增减月数
     * @param num
     * @return
     */
    public static Date addMonth(int num) {
        return addMonth(new Date(), num);
    }

    /**
     * 在日期值上增减月份
     * @param val
     * @param num
     * @return
     */
    public static Date addMonth(Date val, int num) {
        return calendarAdd(val, Calendar.MONTH, num);
    }

    /**
     * 在当前日期值上增减年份
     * @param num
     * @return
     */
    public static Date addYear(int num) {
        return addYear(new Date(), num);
    }

    /**
     * 在日期值上增减年份
     * @param val
     * @param num
     * @return
     */
    public static Date addYear(Date val, int num) {
        return calendarAdd(val, Calendar.YEAR, num);
    }


    /**
     * 对日期进行增减
     * @param value 需要处理的日期
     * @param field 增减类型
     * @param num 增减数
     * @return 增减后的日期
     */
    private static Date calendarAdd(Date value,int field,int num) {
        if (value == null) {
            return value;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(value);
        calendar.add(field, num);
        return calendar.getTime();
    }

    /**
     * 获取本周最小时间
     * @return
     */
    public static Date getWeekMinTime() {
        //设置日期为本周第一天
        GregorianCalendar calendar = new GregorianCalendar();
        int day_of_week = calendar.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        calendar.add(Calendar.DATE , -day_of_week + 1 );
        //设置时间
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本周最大时间
     * @return
     */
    public static Date getWeekMaxTime() {
        //设置日期为本周最后一天
        GregorianCalendar calendar = new GregorianCalendar();
        int day_of_week = calendar.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        calendar.add(Calendar.DATE , -day_of_week + 7 );
        //设置时间
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getDateFromString(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }
    /**
     * @Description:计算开始结束时间之间的差，返回xx天xx小时xx分钟
     * @Author:xiufengd
     * @Date:  2021/2/18 15:42
     * @Param: startTime
     * @Param: endTime
     * @return  @return: String
     * @Throws
     */
    public static String dateDiff(Date startTime,Date endTime){

        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long between=(endTime.getTime()-startTime.getTime())/1000;//除以1000是为了转换成秒
        Long day1=between/(24*3600);
        Long hour1=between%(24*3600)/3600;
        Long minute1=between%3600/60;
        StringBuffer sb = new StringBuffer();
        if(null != day1 && day1 > 0){
            sb.append(day1+"天");
        }
        if(null != hour1 && hour1 > 0){
            sb.append(hour1+"小时");
        }
        if(null != minute1 && minute1 > 0){
            sb.append(minute1+"分钟");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String startDate = DateUtil.getStringDate(DateUtil.addDay(getWeekMinTime(),-7));
        String endDate = DateUtil.getStringDate(DateUtil.addDay(getWeekMaxTime(),-7));

        System.out.println(startDate);
        System.out.println(endDate);
    }
}
