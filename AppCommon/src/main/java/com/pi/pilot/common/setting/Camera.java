package com.pi.pilot.common.setting;

import android.text.TextUtils;

import com.pi.basic.utils.PropertiesUtil;
import com.pi.pilot.common.config.PathConfig;

import java.util.HashMap;

/**
 * @描述：     @相机状态
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public final class Camera {

    public static final String KEY_WORKING            = "WORKING";
    public static final String KEY_STITCHING          = "STITCHING";

    /**相机是否工作**/
    public boolean working;

    /**相机是否拼接**/
    public boolean stitching;

    /**
     * 获取拍照设置
     * @return
     */
    public static final Camera getCamera(){
        final HashMap<String,String> map = PropertiesUtil.get(
                PathConfig.getCameraPropPath(),new String[]{
                        KEY_WORKING,KEY_STITCHING});
        if(map.size() > 0){
            final Camera camera   = new Camera();
            camera.working        = Boolean.parseBoolean(map.get(KEY_WORKING));
            camera.stitching      = Boolean.parseBoolean(map.get(KEY_STITCHING));
            return camera;
        }
        return null;
    }

    /**
     * 设置相机状态
     * @param working
     */
    public static final void setWorking(boolean working){
        PropertiesUtil.set(PathConfig.getCameraPropPath(),KEY_WORKING,String.valueOf(working));
    }

}
