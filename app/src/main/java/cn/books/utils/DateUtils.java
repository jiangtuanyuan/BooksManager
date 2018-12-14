package cn.books.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 蒋 on 2018/9/22.
 * 时间工具类
 */

public class DateUtils {
    /**
     * 获取时间 yyyy-MM-dd hh:mm:ss
     */
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat md = new SimpleDateFormat("MM月dd日");


    private static SimpleDateFormat datedf = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat Timedf = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat TimeHM = new SimpleDateFormat("HH:mm");


    private static String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getDate() {
        try {
            return df.format(System.currentTimeMillis());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 返回当前年月日 2018-10-06
     */
    public static String getYMD() {
        try {
            return ymd.format(System.currentTimeMillis());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取Time
     * HH:mm:ss
     */
    public static String getTime() {
        try {
            return Timedf.format(System.currentTimeMillis());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取Time
     * HH:mm
     */
    public static String getTimeHM() {
        try {
            return TimeHM.format(System.currentTimeMillis());
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 通过日期判断是周几
     */
    public static String DateToDay(String daydate) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        try {
            c.setTime(df.parse(daydate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 通过日期判断是周几 2018-10-02
     */
    public static String DateToDayB(String daydate) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        try {
            c.setTime(ymd.parse(daydate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return dayNames[c.get(Calendar.DAY_OF_WEEK) - 1];
    }


    /**
     * 返回当天是星期几  1 2 3 4 5 6 7
     */
    public static int getWeek() {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * 获取当前 年月日和星期
     * 例如 2018年09月22日
     */
    public static String getYearMonthDayWeek() {
        try {
            return datedf.format(System.currentTimeMillis()) + " [" + DateToDay(getDate()) + "]";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当年月份
     */
    public static int getThisMonth() {
        try {
            return Calendar.getInstance().get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 获取当日
     */
    public static int getThisDay() {
        try {
            return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return -1;
        }
    }


    /**
     * 获取前的年
     */
    public static int getThisYear() {
        try {
            return Calendar.getInstance().get(Calendar.YEAR);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 通过时间 得到月日
     */
    public static String getMD(String date) {

        try {
            return date.substring(5, 10);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 得到指定月份的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        try {
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR, year);
            a.set(Calendar.MONTH, month - 1);
            a.set(Calendar.DATE, 1);
            a.roll(Calendar.DATE, -1);
            int maxDate = a.get(Calendar.DATE);

            return maxDate;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前日期是当年的第多少周
     */
    public static int getThisWeeks() {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 时间戳转换为字符串
     *
     * @param time:时间戳
     * @return
     */
    public static String getToday(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
        return sf.format(d);
    }
}
