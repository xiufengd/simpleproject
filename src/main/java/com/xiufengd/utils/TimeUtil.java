package com.xiufengd.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtil {
    /**
     * 获取时间的字符串
     * @param date
     * @return
     */
    public static String getStringDate(Date date,String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取 当前年、半年、季度、月、日、小时 开始结束时间
     */

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");;
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;


    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public static Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时间
     *
     * @return
     */
    public static Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时间
     *
     * @return
     */
    public static Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的开始时间
     *
     * @return
     */
    public static Date getCurrentHourStartTime() {
        Date now = new Date();
        try {
            now = longHourSdf.parse(longHourSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的结束时间
     *
     * @return
     */
    public static Date getCurrentHourEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(longHourSdf.format(now) + ":59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时间
     *
     * @return
     */
    public static Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 本月的结束时间
     *
     * @return
     */
    public static Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的开始时间
     *
     * @return
     */
    public static Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间
     *
     * @return
     */
    public static Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3){
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6){
                c.set(Calendar.MONTH, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9){
                c.set(Calendar.MONTH, 6);
            } else if (currentMonth >= 10 && currentMonth <= 12){
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取前/后半年的开始时间
     *
     * @return
     */
    public static Date getHalfYearStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    }

    /**
     * 获取前/后半年的结束时间
     *
     * @return
     */
    public static Date getHalfYearEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 判断 某个日期在哪个季度，开始时间和结束时间
     * @param date
     * @return
     */
    public static JSONObject getQuarterByDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        String year = time.substring(0, 4);
        String quarter = time.substring(5, 7);
        int quarterInt = Integer.parseInt(quarter);
        String startTime="";
        String endTime="";
        if (quarterInt >= 1 && quarterInt <= 3){
            startTime = year + "-01-01 00:00:00";
            endTime = year + "-03-31 23:59:59";
        } else if (quarterInt >= 4 && quarterInt <= 6) {
            startTime = year + "-04-01 00:00:00";
            endTime = year + "-06-30 23:59:59";
        } else if (quarterInt >= 7 && quarterInt <= 9) {
            startTime = year + "-07-01 00:00:00";
            endTime = year + "-09-30 23:59:59";
        } else if (quarterInt >= 10 && quarterInt <= 12){
            startTime = year + "-10-01 00:00:00";
            endTime = year + "-12-31 23:59:59";
        }
        JSONObject json = new JSONObject();
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        return json;
    }




    /**
     * 判断 某个日期在哪个季度
     * @param date
     * @return
     */
    public static String getQuarterByDateInfo(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String year = String.valueOf(c.get(Calendar.YEAR));
        int currentMonth = date.getMonth()+1;
        String time="";
        try {
            if (currentMonth >= 1 && currentMonth <= 3){
                time = year+"年第一季度";
            }

            else if (currentMonth >= 4 && currentMonth <= 6){
                time = year+"年第二季度";
            }

            else if (currentMonth >= 7 && currentMonth <= 9){
                time = year+"年第三季度";
            }

            else if (currentMonth >= 10 && currentMonth <= 12){
                time = year+"年第四季度";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 判断 根据年份和季度，确定开始和结束时间
     *
     * @param quarter
     * @return
     */
    public static JSONObject getDateByYearAndQuarter(String  year , String quarter) {
        JSONObject json = new JSONObject();
        String startTime="";
        String endTime="";
        //2017-03-27 17:02:18
        try {
            if(StringUtils.isEmpty(quarter) && StringUtils.isNotEmpty(year) ){
                startTime = year + "-01-01 00:00:00";
                endTime = year + "-12-31 23:59:59";
            }else if(StringUtils.isNotEmpty(quarter) && StringUtils.isNotEmpty(year)){
                if("1".equals(quarter)){
                    startTime = year + "-01-01 00:00:00";
                    endTime = year + "-03-31 23:59:59";
                }else if("2".equals(quarter)){
                    startTime = year + "-04-01 00:00:00";
                    endTime = year + "-06-30 23:59:59";
                }else if("3".equals(quarter)){
                    startTime = year + "-07-01 00:00:00";
                    endTime = year + "-09-30 23:59:59";
                }else if("4".equals(quarter)){
                    startTime = year + "-10-01 00:00:00";
                    endTime = year + "-12-31 23:59:59";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        return json;
    }

    /**
     * 判断 根据年份和 半年，确定开始和结束时间
     * 1 上半年  2 下年年 3  全年
     * @param halfYear
     * @return
     */
    public static String handleYearAndHalfYearOrQuarter(String  year ,String quarter,String halfYear) {
        String dateString="";
        //year 2020  quarter 4
        try {
            if(StringUtils.isNotEmpty(halfYear) && StringUtils.isNotEmpty(year)){
                if("1".equals(halfYear)){
                    dateString= year + "上半年";
                }else if("2".equals(halfYear)){
                    dateString= year + "下半年";
                }else if("3".equals(halfYear)){
                    dateString= year + "全年";
                }
            }else{
                if("1".equals(quarter)){
                    dateString= year + "第一季度";
                }else if("2".equals(quarter)){
                    dateString= year + "第二季度";
                }else if("3".equals(quarter)){
                    dateString= year + "第三季度";
                }else if("4".equals(quarter)){
                    dateString= year + "第四季度";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }


    /**
     * 判断 根据年份和 半年，确定开始和结束时间
     * 1 上半年  2 下年年 3  全年
     * @param halfYear
     * @return
     */
    public static JSONObject getDateByYearAndHalfYear(String  year , String halfYear) {
        JSONObject json = new JSONObject();
        String startTime="";
        String endTime="";
        //2017-03-27 17:02:18
        try {
            if(StringUtils.isNotEmpty(halfYear) && StringUtils.isNotEmpty(year)){
                if("1".equals(halfYear)){
                    startTime = year + "-01-01 00:00:00";
                    endTime = year + "-06-30 23:59:59";
                }else if("2".equals(halfYear)){
                    startTime = year + "-07-01 00:00:00";
                    endTime = year + "-12-31 23:59:59";
                }else if("3".equals(halfYear)){
                    startTime = year + "-01-01 00:00:00";
                    endTime = year + "-12-31 23:59:59";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        return json;
    }

    /**
     * 2020_1 转 2020年第一季度
     * @param yearAndQuarter
     * @return
     */
    public static String getYearAndQuarter(String yearAndQuarter){
        String[] split = yearAndQuarter.split("_");
        return  split[0]+"年第" +GetCH(Integer.parseInt(split[1])) +"季度";
    }

    public static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }



    /**
     * 判断 根据年度季度，计算开始时间和结束时间
     *
     * @param yearAndQuarter
     * @return
     */
    public static JSONObject getTimeByYearAndQuarter(String  yearAndQuarter) {
        JSONObject json = new JSONObject();
        String startTime="";
        String endTime="";
        //2017-03-27 17:02:18
        try {
             if(StringUtils.isNotEmpty(yearAndQuarter) ){
                 String year = yearAndQuarter.substring(0, 4);
                 String quarter = yearAndQuarter.substring(6, 7);
                 if("一".equals(quarter)){
                    startTime = year + "-01-01 00:00:00";
                    endTime = year + "-03-31 23:59:59";
                 }else if("二".equals(quarter)){
                    startTime = year + "-04-01 00:00:00";
                    endTime = year + "-06-30 23:59:59";
                 }else if("三".equals(quarter)){
                    startTime = year + "-07-01 00:00:00";
                    endTime = year + "-09-30 23:59:59";
                 }else if("四".equals(quarter)){
                    startTime = year + "-10-01 00:00:00";
                    endTime = year + "-12-31 23:59:59";
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("startTime",startTime);
        json.put("endTime",endTime);
        return json;
    }


    /**
     * 判断 根据年度和季度 查询 当前时间段的季度
     *
     * @param year
     * @param halfYear
     * @param quarter
     * @return
     */
    public static List<String> queryYearAndQuarter(String  year ,String halfYear,String quarter) {
        List<String> list = new ArrayList<>();
        try {
            if(StringUtils.isNotEmpty(halfYear) ){
                if("3".equals(halfYear)){
                    //全年
                    list.add(year + "年第一季度");
                    list.add(year + "年第二季度");
                    list.add(year + "年第三季度");
                    list.add(year + "年第四季度");
                }else if("1".equals(halfYear)){
                    //上半年
                    list.add(year + "年第一季度");
                    list.add(year + "年第二季度");
                }else if("2".equals(halfYear)){
                    //下半年
                    list.add(year + "年第三季度");
                    list.add(year + "年第四季度");
                }
            }else if(StringUtils.isNotEmpty(quarter)){
                list.add(year + "年第" +GetCH(Integer.parseInt(quarter))+ "季度");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 日期转化
     * @param date
     * @return
     */
    public static String getStartDateInfo(Date date) {
        return shortSdf.format(date) + " 00:00:00";
    }

    /**
     * 日期转化
     * @param date
     * @return
     */
    public static String getEndDateInfo(Date date) {
        return shortSdf.format(date) + " 23:59:59";
    }

}