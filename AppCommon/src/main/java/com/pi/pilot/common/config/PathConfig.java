package com.pi.pilot.common.config;

import android.os.Environment;

import java.io.File;

/**
 * @描述： @路径配置
 * @作者： @蒋诗朋
 * @创建时间： @2017-11-25
 */
public final class PathConfig {

    public static final String CAMERA_DIR_NAME   = "Pisoft";
    public static final String THUMB_DIR_NAME    = "Thumb";
    public static final String PARALLAX_DIR_NAME = "Parallax";
    public static final String CACHE_DIR_NAME     = "Cache";


    private static final String DCIM = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM) + File.separator;

    /**
     * 全景相册路径
     */
    private static final String ALBUM_PATH        = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM)
            + File.separator + CAMERA_DIR_NAME + File.separator;

    /**拍照配置文件**/
    private static final String PHOTO_PROP_PATH                 = DCIM + "PisoftConfig/photo.properties";

    /**录像配置文件**/
    private static final String VIDEO_PROP_PATH                 = DCIM + "PisoftConfig/video.properties";

    /**直播配置文件**/
    private static final String LIVE_PROP_PATH                  = DCIM + "PisoftConfig/live.properties";

    /**FACEBOOK直播配置文件**/
    private static final String FACEBOOK_PROP_PATH              = DCIM + "PisoftConfig/facebook.properties";

    /**YOUTUBE直播配置文件**/
    private static final String YOUTUBE_PROP_PATH               = DCIM + "PisoftConfig/youtube.properties";

    /**WEIBO直播配置文件**/
    private static final String WEIBO_PROP_PATH                 = DCIM + "PisoftConfig/weibo.properties";

    /**公共配置文件**/
    private static final String COMMON_PROP_PATH                = DCIM + "PisoftConfig/common.properties";

    /**相机状态配置文件**/
    private static final String CAMERA_PROP_PATH                = DCIM + "PisoftConfig/camera.properties";

    /**相机配置文件路径**/
    private static final String CAMERA_PISOFT_CONFIG_PATH      = DCIM + "PisoftConfig/";

    /**
     * 全景相册缩略图路径
     */
    private static final String THUMB_PATH        = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM)
            + File.separator + THUMB_DIR_NAME + File.separator;

    /**
     * 背景图
     */
    private static final String PARALLAX_PATH     = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM)
            + File.separator + PARALLAX_DIR_NAME + File.separator +"parallax.jpeg";

    /**
     * 直播图片上传的缓存目录
     */
    private static final String CACHE_PATH        = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM)
            + File.separator + CACHE_DIR_NAME + File.separator;

    /**
     * 创建相册路径
     */
    public final static void makedirs() {
        //创建相册路径
        File file = new File(ALBUM_PATH);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.mkdir();
        }

        //创建缩略图目录
        File thumb = new File(THUMB_PATH);
        if(!thumb.getParentFile().exists()){
            thumb.getParentFile().mkdirs();
        }
        if (!thumb.exists()) {
            thumb.mkdir();
        }
    }

    /**
     * 得到相册路径
     * @return
     */
    public final static String getAlbumPath() {
        return ALBUM_PATH;
    }

    public final static String getThumbPath() {
        return THUMB_PATH;
    }

    public final static String getParallaxPath() {
        return PARALLAX_PATH;
    }

    public final static String getCachePath() {
        return CACHE_PATH;
    }

    /**
     * 获取配置文件路径
     * @return
     */
    public final static String getPisoftConfigPath(){
        return CAMERA_PISOFT_CONFIG_PATH;
    }
    /**
     * 获取照片设置属性
     * @return
     */
    public final static String getPhotoPropPath(){
        return PHOTO_PROP_PATH;
    }

    /**
     * 获取相机状态配置文件路径
     * @return
     */
    public final static String getCameraPropPath(){
        return CAMERA_PROP_PATH;
    }

    /**
     * 获取视频属性文件
     * @return
     */
    public final static String getVideoPropPath(){
        return VIDEO_PROP_PATH;
    }

    /**
     * 获取直播属性文件
     * @return
     */
    public final static String getLivePropPath(){
        return LIVE_PROP_PATH;
    }

    /**
     * 获取FACEBOOK直播属性文件
     * @return
     */
    public final static String getFacebookPropPath() {
        return FACEBOOK_PROP_PATH;
    }

    /**
     * 获取WEIBO直播属性文件
     * @return
     */
    public final static String getWeiboPropPath() {
        return WEIBO_PROP_PATH;
    }

    /**
     * 获取YOUTUBE直播属性文件
     * @return
     */
    public final static String getYoutubePropPath() {
        return YOUTUBE_PROP_PATH;
    }

    /**
     * 获取公共配置文件
     * @return
     */
    public final static String getCommmonPropPath(){
        return COMMON_PROP_PATH;
    }

}
