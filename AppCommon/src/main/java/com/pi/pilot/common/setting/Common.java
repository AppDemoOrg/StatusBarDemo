package com.pi.pilot.common.setting;

import android.text.TextUtils;

import com.pi.basic.utils.PropertiesUtil;
import com.pi.pilot.common.config.PathConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @描述：     @全局设置
 * @作者：     @蒋诗朋
 * @创建时间： @2018-02-07
 */
public final class Common {

    public static final int METER_0                = 0;
    public static final int METER_1                = 24;
    public static final int METER_2                = 51;
    public static final int METER_5                = 76;
    public static final int INFINITY               = 100;

    /**
     * 显示模式保存
     */
    public static final String KEY_MODE               = "MODE";
    /**
     * 拼接距离
     */
    public static final String KEY_STITCH_DISTANCE   = "STITCH_DISTANCE";
    /**
     * 拼接距离
     */
    public static final String KEY_STABLE            = "STABLE";
    /**
     * 上一个媒体文件
     */
    public static final String KEY_LAST_FILE_NAME    = "LAST_FILE_NAME";

    /**当前模式**/
    public int   mode;
    /**拼接距离设置**/
    public int   stitchDistance;
    /**画面稳定设置**/
    public boolean   stable;
    /**最近一个个媒体文件路径**/
    public String lastFileName;

    /**
     * 获取拍照设置
     * @return
     */
    public static final Common getCommon(){
        final HashMap<String,String> map = PropertiesUtil.get(
                PathConfig.getCommmonPropPath(),new String[]{
                        KEY_MODE,KEY_STITCH_DISTANCE,
                        KEY_STABLE, KEY_LAST_FILE_NAME});
        if (map.size() > 0) {
            final Common common    = new Common();
            if (TextUtils.isEmpty(map.get(KEY_MODE))) {
                common.mode = 3;
            } else {
                common.mode = Integer.parseInt(map.get(KEY_MODE));
            }

            if (TextUtils.isEmpty(map.get(KEY_STITCH_DISTANCE))) {
                common.stitchDistance = 0;
            } else {
                common.stitchDistance = Integer.parseInt(map.get(KEY_STITCH_DISTANCE));
            }
            common.stable       = Boolean.parseBoolean(map.get(KEY_STABLE));
            common.lastFileName = map.get(KEY_LAST_FILE_NAME);
            return common;
        }else{
            final File parent   = new
                    File(PathConfig.getPisoftConfigPath());
            if(!parent.exists()){
                parent.mkdirs();
            }

            final File file     = new File(PathConfig.getCommmonPropPath());
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return null;
    }

    /**
     * 保存预览模式
     * @param mode
     */
    public static final void setMode(int mode){
        PropertiesUtil.set(PathConfig.getCommmonPropPath(),KEY_MODE,String.valueOf(mode));
    }

    /**
     * 设置拼接距离
     * @param distance
     */
    public static final void setStitchDistance(int distance){
        PropertiesUtil.set(PathConfig.getCommmonPropPath(),KEY_STITCH_DISTANCE,String.valueOf(distance));
    }

    /**
     * 设置画面稳定
     * @param stable
     */
    public static final void setStable(boolean stable){
        PropertiesUtil.set(PathConfig.getCommmonPropPath(),KEY_STABLE,String.valueOf(stable));
    }

    /**
     * 保存最近的媒体文件名称
     * @param fileName
     */
    public static final void setLastFilePath(String fileName){
        PropertiesUtil.set(PathConfig.getPhotoPropPath(),KEY_LAST_FILE_NAME,fileName);
    }



}
