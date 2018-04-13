package com.pi.basic.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * @描述： @Activity工具类
 * @作者： @蒋诗朋
 * @创建时间： @2017-11-25
 */
public final class ActivityUtil {

    /**
     * 获取当前正在运行的activity名称
     * @param context
     * @return
     */
    public static final String getTopActivityName(Context context){
        ActivityManager manager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        return info.topActivity.getClassName(); //完整类名
    }
}
