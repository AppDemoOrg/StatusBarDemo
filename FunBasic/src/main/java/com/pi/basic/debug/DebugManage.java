package com.pi.basic.debug;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.pi.basic.app.BasicApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * @描述：     @Debug管理器
 * @作者：     @蒋诗朋
 * @创建时间： @2018-01-15
 */
public final class DebugManage {

    /**
     * 初始化
     * @param application
     */
    public static final void initialize(Application application){
//        initLeakCanary(application);
        //initStrictMode();
    }

    //初始化内存检查
    private static final void initLeakCanary(Application application){
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }

    //配置严格模式
    private static final void initStrictMode(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

}
