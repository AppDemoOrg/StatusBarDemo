package com.pi.basic.utils;

/**
 *
 * @描述：      @日期格式
 * @作者：      @蒋诗朋
 * @创建时间：  @2013-11-5
 */

public enum DateStyle {

    YYYY_MM("yyyy-MM", false),
    YYYY_MM_DD("yyyy-MM-dd", false),
    YYYY_MM_DD_EE("yyyy-MM-dd EE", false),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", false),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", false),
    YYYYMMDD_HHMMSS("yyyy:MM:dd HH:mm:ss", false),
    YY_MM_DD_HH_MM_SS("yyMMdd_HHmmss",false),

    YYYY_MM_EN("yyyy/MM", false),
    YYYY_MM_DD_EN("yyyy/MM/dd", false),
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm", false),
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss", false),

    YYYY_MM_CN("yyyy年MM月", false),
    YYYY_MM_DD_CN("yyyy年MM月dd日", false),
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm", false),
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss", false),
    MMMM_YYYY("MMMM yyyy",false),
    DD_MMMM_YYYY("dd MMMM yyyy",false),

    HH_MM("HH:mm", true),
    HH_MM_SS("HH:mm:ss", true),

    EE("EE", true),
    MM("mm", true),
    HH("HH", true),

    MM_DD("MM-dd", true),
    MM_DD_HH_MM("MM-dd HH:mm", true),
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", true),

    MM_DD_EN("MM/dd", true),
    MM_DD_HH_MM_EN("MM/dd HH:mm", true),
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss", true),

    MM_DD_HH_MM_CN("MM月dd日 HH:mm", true),
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss", true);

    private String value;

    private boolean isShowOnly;

    DateStyle(String value, boolean isShowOnly) {
        this.value = value;
        this.isShowOnly = isShowOnly;
    }

    public String getValue() {
        return value;
    }

    public boolean isShowOnly() {
        return isShowOnly;
    }
}
