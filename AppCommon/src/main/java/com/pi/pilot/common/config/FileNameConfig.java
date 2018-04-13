package com.pi.pilot.common.config;

import com.pi.basic.global.GlobalConstant;
import com.pi.basic.utils.DateStyle;
import com.pi.basic.utils.DateUtil;

import java.util.Date;

/**
 * @描述：     @通用工具类
 * @作者：     @黄卫旗
 * @创建时间： @2018-01-17
 */
public class FileNameConfig {

    /** 获取媒体文件名 */
    private static final String getFilePrefix() {
        return DateUtil.dateToString(new Date(), DateStyle.YY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取视频文件名
     * @return
     */
    public static final String getVideoFileName(){
        return PathConfig.getAlbumPath() + getFilePrefix();
    }

    /**
     * 获取照片文件名称
     * @return
     */
    public static final String getPhotoFileName(){
        return PathConfig.getAlbumPath() + getFilePrefix() + GlobalConstant.JPG_SUFFIX;
    }

}
