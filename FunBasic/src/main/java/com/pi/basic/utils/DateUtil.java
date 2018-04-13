package com.pi.basic.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @描述： @日期工具类
 * @作者： @蒋诗朋
 * @创建时间： @2013-11-5
 */

public class DateUtil {

    private static final  ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    private static final  Object object              = new Object();

    private static final  String TIME_DIVIDER        = ":";
    private static final  String TIME_SECONDS_ZERO   = "00";
    private static final  String TIME_SECONDS_THIRTY = "30";

    public static final String[] formatTime(long time) {
        SimpleDateFormat dfsDay = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat dfsTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String[] ret = new String[3];
        Date date = new Date(time);
        ret[0] = dfsDay.format(date);
        ret[2] = dfsTime.format(date);

        switch (date.getMonth()) {
            case 0:
                ret[1] = "一月";
                break;
            case 1:
                ret[1] = "二月";
                break;
            case 2:
                ret[1] = "三月";
                break;
            case 3:
                ret[1] = "四月";
                break;
            case 4:
                ret[1] = "五月";
                break;
            case 5:
                ret[1] = "六月";
                break;
            case 6:
                ret[1] = "七月";
                break;
            case 7:
                ret[1] = "八月";
                break;
            case 8:
                ret[1] = "九月";
                break;
            case 9:
                ret[1] = "十月";
                break;
            case 10:
                ret[1] = "十一月";
                break;
            case 11:
                ret[1] = "十二月";
                break;
        }

        return ret;
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static final SimpleDateFormat getDateFormat(String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static final int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期字符串
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期字符串
     */
    private static final String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = stringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString  = dateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期
     */
    private static final Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static final Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i)
                                - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i),
                                timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
                                : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date 日期字符串
     * @return true or false
     */
    public static final boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (getDateStyle(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date 日期字符串
     * @return 日期风格
     */
    public static final DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {

                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static final Date stringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return stringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static final Date stringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {

            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date      日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static final Date stringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = stringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static final String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 长整型日期转字符串
     * @param date
     * @param format
     * @return
     */
    public static final String longToString(long date, String format) {
        String dateString = getDateFormat(format).format(date);
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static final String dateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date       旧日期字符串
     * @param newPattern 新日期格式
     * @return 新日期字符串
     */
    public static final String stringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return stringToString(date, oldDateStyle, newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static final String stringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return stringToString(date, oldDateStyle, newDateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date        旧日期字符串
     * @param olddPattern 旧日期格式
     * @param newPattern  新日期格式
     * @return 新日期字符串
     */
    public static final String stringToString(String date, String olddPattern,
                                        String newPattern) {
        return dateToString(stringToDate(date, olddPattern), newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newParttern  新日期格式
     * @return 新日期字符串
     */
    public static final String stringToString(String date, DateStyle olddDteStyle,
                                        String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = stringToString(date, olddDteStyle.getValue(),
                    newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddPattern  旧日期格式
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static final String stringToString(String date, String olddPattern,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = stringToString(date, olddPattern,
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static final String stringToString(String date, DateStyle olddDteStyle,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = stringToString(date, olddDteStyle.getValue(),
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static final String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static final Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static final String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static final Date addMonth(Date date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static final String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static final Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期字符串
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static final String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static final Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期字符串
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static final String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date 日期
     *             增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static final Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date 日期字符串
     * @param
     * @return 增加秒钟后的日期字符串
     */
    public static final String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date 日期
     * @param
     * @return 增加秒钟后的日期
     */
    public static final Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期字符串
     * @return 年份
     */
    public static final int getYear(String date) {
        return getYear(stringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期
     * @return 年份
     */
    public static final int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期字符串
     * @return 月份
     */
    public static final int getMonth(String date) {
        return getMonth(stringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期
     * @return 月份
     */
    public static final int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期字符串
     * @return 天
     */
    public static final int getDay(String date) {
        return getDay(stringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期
     * @return 天
     */
    public static final int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期字符串
     * @return 小时
     */
    public static final int getHour(String date) {
        return getHour(stringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期
     * @return 小时
     */
    public static final int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 分钟
     */
    public static final int getMinute(String date) {
        return getMinute(stringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期
     * @return 分钟
     */
    public static final int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 秒钟
     */
    public static final int getSecond(String date) {
        return getSecond(stringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期
     * @return 秒钟
     */
    public static final int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);

    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static final String getDate(String date) {
        return stringToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期
     */
    public static final String getDate(Date date) {
        return dateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间
     */
    public static final String getTime(String date) {
        return stringToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期
     * @return 时间
     */
    public static final String getTime(Date date) {
        return dateToString(date, DateStyle.HH_MM_SS);
    }


    /**
     * 整形时间转化成小时，分钟
     *
     * @param
     * @return
     */
    public static final String longToString(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static final String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期字符串
     * @return 星期
     */
    public static final Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = stringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static final Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }

    public static final int getWeekNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekNumber;
    }

    public static final Week getWeek(int weekNumber) {
        Week week = null;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param date      日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */
    public static final int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(stringToDate(date), stringToDate(otherDate));
    }

    /**
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数。如果失败则返回-1
     */
    public static final int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = stringToDate(DateUtil.getDate(date),
                DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtil.stringToDate(DateUtil.getDate(otherDate),
                DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 获取本周星期一的时间
     *
     * @return
     */
    public static final String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        c.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(c.getTime());
    }

    /**
     * 取下周一日期
     *
     * @return
     */
    public static final String getMondayOfNextWeek() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1); // 当周周一
        calendar.add(Calendar.DATE, 7);
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(calendar.getTime());
    }

    /**
     * 取当周周日
     */
    public static final String getSundayOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1);
        // 以上取得的是周一
        calendar.add(Calendar.DATE, 6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 取上周周一
     */
    public static final String getMondayOfPreviousWeek() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1);
        calendar.add(Calendar.DATE, -7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 取上周周日
     */
    public static final String getSundayOfPreviousWeek() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1);
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 循环取得上周日的日期
     */
    public static final String getSundayDate(int lastIndex) {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, lastIndex * 7);
        calendar.add(Calendar.DATE, -day_of_week + 1);
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 循环取得往前的周六的时间
     */
    public static final String getSaturdayDate(int endIndex) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, endIndex * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }


    // 获取当前日期与本周日的相差天数
    public static final int getDaysOfNow2SundayInWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 减一天，周一为1，符合中国人习惯。Sunday
        if (dayOfWeek == 0) { // 周日
            return 0;
        } else {
            return 0 - dayOfWeek;
        }
    }

    /**
     * 获取本周六的日期
     **/
    public static final String getDateOfSaturdayInWeek() {
        int day = getDaysOfNow2SundayInWeek() + 6; // 加6，即周六离本周日的间隔天数
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.add(GregorianCalendar.DATE, day); // 计算与本周六相差的时间间隔
        Date curDay = gCalendar.getTime();
        return dateToString(curDay, DateStyle.YYYY_MM_DD);
    }

    /**
     * 取下周周日
     */
    public static final String getSundayOfNextWeek() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1); // 当周周一
        calendar.add(Calendar.DATE, 13);
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(calendar.getTime());
    }

    /**
     * 取当月1号
     */
    public static final String getFirstDayOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1); // 设为当前月的1号
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(calendar.getTime());
    }

    /**
     * 取上月最后一天
     */
    public static final String getLastDayOfPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1); // 设为当前月的1号
        calendar.add(Calendar.DATE, -1); // 减去一天，变为上月最后一天
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(calendar.getTime());
    }

    /**
     * 取下月1号
     */
    public static final String getFirstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 把日期设置为当月第一天
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(calendar.getTime());
    }

    /**
     * 取下月最后一天
     */
    public static final String getLastDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);// 加一个月
        calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        SimpleDateFormat sdf = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取本月最后一天时间
     *
     * @return
     */
    public static final String getLastDateOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD.getValue());
        Date dt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, days);
        String result = format.format(cal.getTime());
        return result;
    }

    /**
     * 获得本年有多少天
     */
    public static final int getTotalDaysOfThisYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        calendar.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int totalDays = calendar.get(Calendar.DAY_OF_YEAR);
        return totalDays;
    }

    /**
     * 获取某年某月的最后一天
     */
    public static final int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     */
    public static final boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 获得本年第一天的日期
     */
    public static final Date getFirstDayOfThisYear() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    public static final Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }


    /**
     * 获得本年最后一天的日期
     */
    public static final Date getLastDayOfThisYear() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    public static final Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 获得上年第一天的日期 *
     */
    public static final String getFirstDayOfPreviousYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1); // 若是 明年改为正整数1即可
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date date = new Date(calendar.getTimeInMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 两个时间之间的天数
     */

    /**
     * 比较两个日期之间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static final long compareDateOffset(String date1, String date2) {
        if (date1 == null || "".equals(date1.trim())) {
            return 0;
        }
        if (date2 == null || "".equals(date2.trim())) {
            return 0;
        }
        // 转换为标准时间
        Date date = null;
        Date mydate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
        try {
            date = sdf.parse(date1);
            mydate = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long day = 0;
        if (date.before(mydate)) {
            day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
        } else {
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        }
        return day;
    }

    /**
     * utc时间转化为string日期格式
     * @param utcTime
     * @param dateStyle
     * @return
     */
    public static final String UTCToString(String utcTime,DateStyle dateStyle){
        utcTime = utcTime.replace("Z", " UTC");
        final SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyyMMdd'T'HHmmss.SSS Z");
        try {
            final Date date        = sdf.parse(utcTime);
            return dateToString(date,dateStyle);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final int equalsDays(String selectTime, String toadyTime) {
        int result = 0;
        // 转换为标准时间
        Date selectDate = null;
        Date todayDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
        try {
            selectDate = sdf.parse(selectTime);
            todayDate = sdf.parse(toadyTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (selectDate != null && todayDate != null) {
            if (selectDate.getTime() > todayDate.getTime()) {
                result = 1;
            } else if (selectDate.getTime() < todayDate.getTime()) {
                result = -1;
            }
        }
        return result;
    }

    /**
     * 秒转化成分钟
     *
     * @param second
     * @return
     */
    public static final int secondToMinute(int second) {
        return second / 60;
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static final String getStandardDate(String timeStr) {
        StringBuffer sb = new StringBuffer();
        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);// 秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 循环取得上周日的日期
     */
    public static final String getNewSundayDate(int Index) {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -day_of_week);
        calendar.add(Calendar.DATE, Index * 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 循环取得往前的周六的时间
     */
    public static final String getNewSaturdayDate(int Index) {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int days = 6 - day_of_week;
        calendar.add(Calendar.DATE, days);
        calendar.add(Calendar.DATE, Index * 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取一个月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static final int getDayOfMonth(int year, int month) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(year, month - 1, Calendar.DATE);
        return aCalendar.getActualMaximum(Calendar.DATE);
    }

    public static final String getWeekDay(int weekDay) {
        String result = null;
        switch (weekDay) {
            case 2:
                result = "周一";
                break;
            case 3:
                result = "周二";
                break;
            case 4:
                result = "周三";
                break;
            case 5:
                result = "周四";
                break;
            case 6:
                result = "周五";
                break;
            case 7:
                result = "周六";
                break;
            case 1:
                result = "周日";
                break;

        }
        return result;

    }

    public static final String getTimestampString(Date date) {
        String formatString = null;
        long time = date.getTime();
        if (isSameDay(time)) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            int var5 = calendar.get(11);
            if (var5 > 17) {
                formatString = "晚上 hh:mm";
            } else if (var5 >= 0 && var5 <= 6) {
                formatString = "凌晨 hh:mm";
            } else if (var5 > 11 && var5 <= 17) {
                formatString = "下午 hh:mm";
            } else {
                formatString = "上午 hh:mm";
            }
        } else if (isYesterday(time)) {
            formatString = "昨天 HH:mm";
        } else {
            formatString = "M月d日 HH:mm";
        }

        return (new SimpleDateFormat(formatString, Locale.CHINA)).format(date);
    }


    private static final boolean isSameDay(long time) {
        TimeInfo timeInfo = getTodayStartAndEndTime();
        return time > timeInfo.startTime && time < timeInfo.endTime;
    }

    private static final boolean isYesterday(long time) {
        TimeInfo timeInfo = getYesterdayStartAndEndTime();
        return time > timeInfo.startTime && time < timeInfo.endTime;
    }

    public static final TimeInfo getYesterdayStartAndEndTime() {
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.add(5, -1);
        sCalendar.set(11, 0);
        sCalendar.set(12, 0);
        sCalendar.set(13, 0);
        sCalendar.set(14, 0);
        Date startDate = sCalendar.getTime();
        long startTime = startDate.getTime();
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.add(5, -1);
        eCalendar.set(11, 23);
        eCalendar.set(12, 59);
        eCalendar.set(13, 59);
        eCalendar.set(14, 999);
        Date endDate = eCalendar.getTime();
        long endTime = endDate.getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.startTime = startTime;
        timeInfo.endTime = endTime;
        return timeInfo;
    }


    public static final TimeInfo getTodayStartAndEndTime() {
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.set(11, 0);
        sCalendar.set(12, 0);
        sCalendar.set(13, 0);
        sCalendar.set(14, 0);
        Date startDate = sCalendar.getTime();
        long startTime = startDate.getTime();
        Calendar sCanlendar = Calendar.getInstance();
        sCanlendar.set(11, 23);
        sCanlendar.set(12, 59);
        sCanlendar.set(13, 59);
        sCanlendar.set(14, 999);
        Date endDate = sCanlendar.getTime();
        long endTime = endDate.getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.startTime = startTime;
        timeInfo.endTime = endTime;
        return timeInfo;
    }

    public static final String getNextTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String format = simpleDateFormat.format(new Date());
        String[] split = format.split(":");
        String end = getTimeEnd(split);
        String start = split[0];
        int time = Integer.valueOf(split[1]).intValue() + 15;
        if (time > 60 || time > 30) {
            int i = Integer.parseInt(split[0]) + 1;
            DecimalFormat decimalFormat = new DecimalFormat("00");
            if (i >= 24) {
                i = 0;
            }
            start = decimalFormat.format(i);
        }
        return start + TIME_DIVIDER + end;
    }

    private static final String getTimeEnd(String[] split) {
        int time = Integer.valueOf(split[1]).intValue() + 15;

        if (time <= 60 && time > 30) {
            return TIME_SECONDS_ZERO;
        }
        if (time > 60 || time < 30 || split[1].equals("15")) {
            return TIME_SECONDS_THIRTY;
        }
        return TIME_SECONDS_ZERO;
    }

    public static final String transferTime(long time) {
        time = time / (1000 * 60l);//分钟

        if (time < 10) {
            return "刚刚";
        }

        if (time < 60) {
            return time + "分钟前";
        }
        time = time / 60l;

        if (time < 24) {
            return time + "小时前";
        }

        time = time / 24;//天数

        if (time < 30) {
            return time + "天前";
        }

        time = time / 30;//月数

        if (time < 6) {
            return time + "个月前";
        }

        if (time < 12) {
            return "半年前";
        }
        time = time / 12;
        if (time > 1) {
            return time + "年前";
        }
        return "0";
    }

    /**
     * 转换时间(约会模块使用)
     *
     * @param sourceTime "yyyy-MM-dd HH:mm:ss"
     * @return 今天、明天 或 "yyyy-MM-dd HH:mm:ss"
     */
    public static final String timeFormat(String sourceTime) {
        String result = null;
        if (!TextUtils.isEmpty(sourceTime)) {
            try {
                sourceTime = toRepeatDate(sourceTime);
                if (TextUtils.isEmpty(sourceTime)) {
                    return null;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                for (int i = -2; i < 3; i++) {
                    result = getTargetTimeString(formatter, calendar, sourceTime, i);
                    if (!TextUtils.isEmpty(result)) {
                        return result;//直接返回，结束循环
                    }
                }
                //都没有匹配到，直接返回日期
                result = sourceTime;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static final String getTargetTimeString(SimpleDateFormat formatter,
                                              Calendar calendar, String sourceTime, int offset) {
        String returnString = null;
        calendar.add(Calendar.DATE, offset);//设置日期的偏移
        Date date = calendar.getTime(); //这个时间就是日期往后或往前推offset天的结果
        String dateString = toRepeatDate(formatter.format(date.getTime()));
        if (TextUtils.isEmpty(dateString)) {
            return null;
        }
        switch (offset) {
            case -2:
                if(sourceTime.equals(dateString)){
                    returnString = "前天";
                }
                break;
            case -1:
                if(sourceTime.equals(dateString)){
                    returnString = "昨天";
                }
                break;
            case 0:
                if(sourceTime.equals(dateString)){
                    returnString = "今天";
                }
                break;
            case 1:
                if(sourceTime.equals(dateString)){
                    returnString = "明天";
                }
                break;
            case 2:
                if(sourceTime.equals(dateString)){
                    returnString = "后天";
                }
                break;
        }
        calendar.add(Calendar.DATE, -offset);//重置
        return returnString;
    }

    /**
     * 补全日期的缺省的0
     *
     * @param date 原数据 2016-1-25
     * @return yyyy-MM-dd
     */
    private static final String toRepeatDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        String[] arr = date.split("-");
        if (arr.length != 3) {
            return null;
        }
        if (arr[0].length() != 4) {
            return null;
        }
        if (arr[1].length() > 2 || arr[1].length() < 1) {
            return null;
        } else {
            if (arr[1].length() == 1) {
                arr[1] = "0" + arr[1];
            }
        }
        if (arr[2].length() > 2 || arr[2].length() < 1) {
            return null;
        } else {
            if (arr[2].length() == 1) {
                arr[2] = "0" + arr[2];
            }
        }
        return arr[0] + "-" + arr[1] + "-" + arr[2];
    }
}
