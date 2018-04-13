package com.pi.pilot.common.setting;

import android.text.TextUtils;

import com.pi.basic.utils.PropertiesUtil;
import com.pi.pilot.common.config.PathConfig;

import java.util.HashMap;

/**
 * @描述：     @视频设置
 * @作者：     @蒋诗朋
 * @创建时间： @2017-01-31
 */
public final class Video {
    /**
     * 8k
     */
    public static final String RESOLUTION_7680_3840 = "7680*3840";
    /**
     * 6k
     */
    public static final String RESOLUTION_5760_2880 = "5760*2880";
    /**
     * 4k
     */
    public static final String RESOLUTION_3840_1920 = "3840*1920";
    /**
     * 2k
     */
    public static final String RESOLUTION_1920_960  = "1920*960";

    public static final String KEY_RESULOTION        = "RESULOTION";
    public static final String KEY_DELAY_IS_OPEN     = "DELAY_IS_OPEN";
    public static final String KEY_DELAY_TIME        = "DELAY_TIME";
    public static final String KEY_RETAIN_FISH_EYE   = "RETAIN_FISH_EYE";

    /**分辨率**/
    public String   resulotion;
    /**延迟是否打开**/
    public boolean delayIsOpen;
    /**延迟时间**/
    public int delayTime;
    /**是否保留原始鱼眼图**/
    public boolean retainFishEye;
    /**大小**/
    public Size size;

    /**
     * 获取拍照设置
     * @return
     */
    public static final Video getVideo(){
        final HashMap<String,String> map = PropertiesUtil.get(
                PathConfig.getVideoPropPath(),new String[]{
                        KEY_RESULOTION,KEY_DELAY_IS_OPEN,
                        KEY_DELAY_TIME, KEY_RETAIN_FISH_EYE});
        if(map.size() > 0){
            final Video video      = new Video();
            if (TextUtils.isEmpty(map.get(KEY_RESULOTION))) {
                video.resulotion       = "7680*3840";
            } else {
                video.resulotion       = map.get(KEY_RESULOTION);
            }

            video.size             = Size.getSize(video.resulotion);

            video.delayIsOpen      = Boolean.parseBoolean(map.get(KEY_DELAY_IS_OPEN));

            if (TextUtils.isEmpty(map.get(KEY_DELAY_TIME))) {
                video.delayTime       = 0;
            } else {
                video.delayTime       = Integer.parseInt(map.get(KEY_DELAY_TIME));
            }
            video.retainFishEye   = Boolean.parseBoolean(map.get(KEY_RETAIN_FISH_EYE));
            return video;
        }
        return null;
    }

    /**
     * 延迟是否打开
     * @param isOpen
     */
    public static final void setDelayOpen(boolean isOpen){
        PropertiesUtil.set(PathConfig.getVideoPropPath(),KEY_DELAY_IS_OPEN,String.valueOf(isOpen));
    }

    /**
     * 是否保留原始鱼眼图
     * @param isOpen
     */
    public static final void setRetainFishEye(boolean isOpen){
        PropertiesUtil.set(PathConfig.getVideoPropPath(),KEY_RETAIN_FISH_EYE,String.valueOf(isOpen));
    }
    /**
     * 设置延迟
     * @param delay
     */
    public static final void setDelay(int delay){
        PropertiesUtil.set(PathConfig.getVideoPropPath(),KEY_DELAY_TIME,String.valueOf(delay));
    }

    /**
     * 设置分辨率
     * @param resolution
     */
    public static final void setResolution(String resolution){
        PropertiesUtil.set(PathConfig.getVideoPropPath(),KEY_RESULOTION,resolution);
    }


    public  static final class Size{
        public int width;
        public int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Size() {

        }

        /**
         * 获取分辨率
         * @param resulotion
         * @return
         */
        public static final Size getSize(String resulotion){
            final Size size = new Size(3840,1920);
            if(RESOLUTION_7680_3840.equals(resulotion)){
                size.width  = 7680;
                size.height = 3840;
            }else if(RESOLUTION_5760_2880.equals(resulotion)){
                size.width  = 5760;
                size.height = 2880;
            }else if(RESOLUTION_3840_1920.equals(resulotion)){
                size.width  = 3840;
                size.height = 1920;
            }else if(RESOLUTION_1920_960.equals(resulotion)){
                size.width  = 1920;
                size.height = 960;
            }
            return size;
        }
    }

}
