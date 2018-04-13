package com.pi.pilot.common.util;

import android.app.AlarmManager;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;

import com.pi.pilot.common.R;
import com.pi.pilot.common.base.SwipBackApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 29606 on 2018/3/21.
 * 时间设置
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class TimeSettingUtil {

    //判断系统时间是否自动设置
    public static boolean isSystemTimeSettingAutoly() {
        try {
            return Settings.Global.getInt(SwipBackApplication.getAppContext().getApplicationContext().getContentResolver(),
                    Settings.Global.AUTO_TIME) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //修改自动设置
    public static void setSystemTimeAuto(boolean isAuto) {
        Settings.Global.putInt(SwipBackApplication.getAppContext().getApplicationContext().getContentResolver(),
                Settings.Global.AUTO_TIME, isAuto ? 1 : 0);
        Settings.Global.putInt(SwipBackApplication.getAppContext().getApplicationContext().getContentResolver(),
                Settings.Global.AUTO_TIME_ZONE, isAuto ? 1 : 0);
    }

    //判断是否24小时制
    public static boolean is24HourSystem() {
        return DateFormat.is24HourFormat(SwipBackApplication.getAppContext().getApplicationContext());
    }

    //设置24小时制
    public static void set24HourSystem(boolean is24HourSystem) {
        Settings.System.putString(SwipBackApplication.getAppContext().getApplicationContext().getContentResolver(),
                Settings.System.TIME_12_24, is24HourSystem ? "24" : "12");
    }

    //获取时区
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return formatTimeZoneShort(tz);
    }

    //获取时区
    public static String getCurrentTimeZoneGMT() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.LONG_GMT);
    }

    public static String formatTimeZoneShort(TimeZone timeZone) {
        return (timeZone.getDisplayName(false, TimeZone.SHORT_GENERIC) + " " + timeZone.getDisplayName(false, TimeZone.LONG_GMT)).replace("时间", "");
    }

    //設置时区
    public static void setTimeZone(String timeZoneId) {
        AlarmManager mAlarmManager = (AlarmManager) SwipBackApplication.getAppContext().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setTimeZone(timeZoneId);
    }

    //根据郝妙书获取日期
    public static String getDateFromTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
    }

    //根据郝妙书获取时分
    public static String getHourAndMinuteFromTime(long time) {
        boolean is24HourSystem = is24HourSystem();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = is24HourSystem ? calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR);
        if (hour == 0 && !is24HourSystem) {
            hour = 12;
        }
        int minute = calendar.get(Calendar.MINUTE);
        String frontStr = is24HourSystem ? "" : SwipBackApplication.getAppContext().getApplicationContext().
                getString((calendar.get(Calendar.AM_PM) == Calendar.AM) ? R.string.time_setting_morning : R.string.time_setting_afternoon);
        return hour + ":" + (minute >= 10 ? (minute + "") : ("0" + minute)) + " " + frontStr;
    }

    public static long getTimeFromDateAndHour(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
    }

    //设置系统日期
    public static void setSysDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) SwipBackApplication.getAppContext().getApplicationContext().getSystemService(
                    SwipBackApplication.getAppContext().getApplicationContext().ALARM_SERVICE)).setTime(when);
        }
    }

    //设置系统时间
    public static void setSysTime(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) SwipBackApplication.getAppContext().getApplicationContext().getSystemService(
                    SwipBackApplication.getAppContext().getApplicationContext().ALARM_SERVICE)).setTime(when);
        }
    }

    //时间格式化
    public static String formatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    //获取前后15年年份
    public static String[] getYears() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String[] yearStrs = new String[30];
        for (int i = 0; i < 30; i++) {
            yearStrs[i] = String.valueOf((year - 15 + i));
        }
        return yearStrs;
    }

    //获取所有月份
    public static String[] getMonths() {
        String[] monthStrs = new String[12];
        for (int i = 0; i < 12; i++) {
            monthStrs[i] = String.valueOf((1 + i));
        }
        return monthStrs;
    }

    //获取每月天数
    public static String[] getDays(int year, int month) {
        String[] monthStrs = new String[getDaysByYearMonth(year, month)];
        for (int i = 0; i < monthStrs.length; i++) {
            monthStrs[i] = (i + 1) + "";
        }
        return monthStrs;
    }

    //获取小时列表
    public static String[] getHours(boolean is24HourSystem) {
        String[] hours = new String[is24HourSystem ? 24 : 12];
        for (int i = 0; i < hours.length; i++) {
            if (i == 0 && !is24HourSystem) {
                hours[i] = 12 + "";
            } else {
                hours[i] = i + "";
            }
        }
        return hours;
    }

    //获取分钟列表
    public static String[] getMinutes() {
        String[] minutes = new String[60];
        for (int i = 0; i < minutes.length; i++) {
            minutes[i] = i + "";
        }
        return minutes;
    }

    //获取上下午
    public static String[] getAps() {
        return new String[]{SwipBackApplication.getAppContext().getApplicationContext().getString(R.string.time_setting_morning)
                , SwipBackApplication.getAppContext().getApplicationContext().getString(R.string.time_setting_afternoon)};
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    //列表专数组
    private static String[] toArray(List<String> array) {
        String[] strings = new String[array.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = array.get(i);
        }
        return strings;
    }
}
