package com.pi.pilot.common.setting;

import android.text.TextUtils;

import com.pi.basic.utils.PropertiesUtil;
import com.pi.pilot.common.config.PathConfig;

import java.util.HashMap;

/**
 * @描述：     @拍照设置
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public final class Photo {
    /**
     * 10k
     */
    public static final String RESOLUTION_10000_5000 = "10000*5000";
    /**
     * 8k
     */
    public static final String RESOLUTION_8192_4096  = "8192*4096";
    /**
     * 6k
     */
    public static final String RESOLUTION_6144_3072  = "6144*3072";
    /**
     * 4k
     */
    public static final String RESOLUTION_4096_2048  = "4096*2048";
    /**
     * 3k
     */
    public static final String RESOLUTION_3072_1536  = "3072*1536";
    /**
     * 2k
     */
    public static final String RESOLUTION_2048_1024  = "2048*1024";

    public static final String KEY_RESULOTION        = "RESULOTION";
    public static final String KEY_DELAY_IS_OPEN     = "DELAY_IS_OPEN";
    public static final String KEY_DELAY_TIME        = "DELAY_TIME";
    public static final String KEY_POST_PROCESS      = "POST_PROCESS";
    public static final String KEY_RETAIN_FISH_EYE   = "RETAIN_FISH_EYE";

    /**分辨率**/
    public String   resulotion;
    /**延迟是否打开**/
    public boolean delayIsOpen;
    /**延迟时间**/
    public int delayTime;
    /**后处理是否打开**/
    public boolean postProcess;
    /**
     * 是否保留原始鱼眼图
     */
    public boolean retainFishEye;

    /**大小**/
    public Size size;

    /**
     * 获取拍照设置
     * @return
     */
    public static final Photo getPhoto(){
        final HashMap<String,String> map = PropertiesUtil.get(
                PathConfig.getPhotoPropPath(),new String[]{
                        KEY_RESULOTION,KEY_DELAY_IS_OPEN,
                        KEY_DELAY_TIME,KEY_POST_PROCESS,
                        KEY_RETAIN_FISH_EYE});
        if(map.size() > 0){
            final Photo photo   = new Photo();
            photo.resulotion   = map.get(KEY_RESULOTION);
            photo.size          = Size.getSize(photo.resulotion);
            photo.delayIsOpen  = Boolean.parseBoolean(map.get(KEY_DELAY_IS_OPEN));
            if (TextUtils.isEmpty(map.get(KEY_DELAY_TIME))) {
                photo.delayTime    = 0;
            } else {
                photo.delayTime    = Integer.parseInt(map.get(KEY_DELAY_TIME));
            }
            photo.postProcess  = Boolean.parseBoolean(map.get(KEY_POST_PROCESS));
            photo.retainFishEye= Boolean.parseBoolean(map.get(KEY_RETAIN_FISH_EYE));
            return photo;
        }
        return null;
    }

    /**
     * 延迟是否打开
     * @param isOpen
     */
    public static final void setDelayOpen(boolean isOpen){
        PropertiesUtil.set(PathConfig.getPhotoPropPath(),KEY_DELAY_IS_OPEN,String.valueOf(isOpen));
    }

    /**
     * 是否打开后处理
     */
    public static final void setProcessOpen(boolean isOpen){
        PropertiesUtil.set(PathConfig.getPhotoPropPath(),KEY_POST_PROCESS,String.valueOf(isOpen));
    }

    /**
     * 是否保留原始鱼眼图
     * @param retain
     */
    public static final void setRetainFishEye(boolean retain){
        PropertiesUtil.set(PathConfig.getPhotoPropPath(),KEY_RETAIN_FISH_EYE,String.valueOf(retain));
    }

    /**
     * 设置延迟
     * @param delay
     */
    public static final void setDelay(int delay){
        PropertiesUtil.set(PathConfig.getPhotoPropPath(),KEY_DELAY_TIME,String.valueOf(delay));
    }

    /**
     * 设置分辨率
     * @param resolution
     */
    public static final void setResolution(String resolution){
        PropertiesUtil.set(PathConfig.getPhotoPropPath(),KEY_RESULOTION,resolution);
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
            final Size size = new Size(8192,4096);
            if(RESOLUTION_10000_5000.equals(resulotion)){
                size.width  = 10000;
                size.height = 5000;
            }else if(RESOLUTION_8192_4096.equals(resulotion)){
                size.width  = 8192;
                size.height = 4096;
            }else if(RESOLUTION_6144_3072.equals(resulotion)){
                size.width  = 6144;
                size.height = 3072;
            }else if(RESOLUTION_4096_2048.equals(resulotion)){
                size.width  = 4096;
                size.height = 2048;
            }else if(RESOLUTION_3072_1536.equals(resulotion)){
                size.width  = 3072;
                size.height = 1536;
            }else if(RESOLUTION_2048_1024.equals(resulotion)){
                size.width  = 2048;
                size.height = 1024;
            }
            return size;
        }
    }

}
