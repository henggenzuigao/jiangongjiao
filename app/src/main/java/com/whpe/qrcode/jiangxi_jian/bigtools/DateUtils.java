package com.whpe.qrcode.jiangxi_jian.bigtools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 129674 on 2018/4/12.
 */

public class DateUtils {
    public static String getWeichatTimestamp(){

        return System.currentTimeMillis()+"";
    }

    public static String getNowDateyyyyMMddhhmmss(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }


    /*描述:获取下一个月文本形式
     *
             * @return
             */
    public static String getNextMonthFormat(String format) {
        SimpleDateFormat dft = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return dft.format(calendar.getTime());
    }
    /*描述:获取当月文本形式
     *
             * @return
             */
    public static String getThisMonthFormat(String format) {
        SimpleDateFormat dft = new SimpleDateFormat(format);
        Date d = new Date();

        return dft.format(d);
    }


    /**
     * 将yyyyMMdd形式转换成dd形式
     * @param st_date
     * @return
     */
    public static String yyyyMMddTodd(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("dd");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    /**
     * 将yyyyMMdd形式转换成yyyy年MM月dd日形式
     * @param st_date
     * @return
     */
    public static String yyyyMMddToShowyyyyMMdd(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    /**
     * 将HHmm形式转换成HH:mm形式
     * @param st_date
     * @return
     */
    public static String HHmmToShowHHmm(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("HH:mm");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    /**
     * 将HH:mm形式转换成HHmm形式
     * @param st_date
     * @return
     */
    public static String ShowHHmmToHHmm(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("HHmm");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }



    public static String getNowtext(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("MM-dd HH:mm");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    public static String getNowtext(String st_date,String gs){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat(gs);
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    public static String getNowDatetext(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    public static long getBetween(long new_time,long old_time){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        long second=0;
        try
        {
            Date d1 = df.parse(""+new_time);
            Date d2 = df.parse(""+old_time);
            long diff = d1.getTime() - d2.getTime();
            second = diff / (1000);
        }
        catch (Exception e)
        {
        }
        return second;
    }

    /**
     * 通过指定的年份和月份获取当月有多少天.
     *
     * @param year  年.
     * @param month 月.
     * @return 天数.
     */
    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 获取指定年月的 1 号位于周几.
     * @param year  年.
     * @param month 月.
     * @return      周.
     */
    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 0);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
