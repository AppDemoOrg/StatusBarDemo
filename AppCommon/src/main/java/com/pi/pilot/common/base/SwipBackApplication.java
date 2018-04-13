package com.pi.pilot.common.base;

import com.bugtags.library.Bugtags;
import com.pi.basic.app.BasicApplication;
import com.pi.basic.concurrent.ThreadPool;
import com.pi.pilot.common.system.NetworkChangeListener;
import com.pi.pilot.common.system.SystemChangeListener;
import com.pi.pilot.common.system.SystemManage;
import com.pi.pilot.support.swipback.SwipBackConfig;
import com.pi.pilot.support.swipback.SwipBackManager;

import java.util.Locale;

/**
 * @描述：     @应用上层应用基类
 * @作者：     @蒋诗朋
 * @创建时间： @2018-02-01
 */
public abstract class SwipBackApplication extends BasicApplication {

    protected SystemManage mSystemManage;

    public abstract void executeAsycInit();

    @Override
    public void initComplete() {
        initSwipBack();
        ThreadPool.Builder.single().setImmediatelyShutdown(true).
                builder().execute(new Runnable() {
            @Override
            public void run() {
                executeAsycInit();
            }
        });
        //initBugs();
        initSystem();
    }

    private final void initSwipBack(){
        SwipBackManager.initialize(this,new SwipBackConfig.Builder().
                edgeOnly(false).lock(false).rotateScreen(false).create());
    }

    //加载bugs框架
    private final void initBugs(){
        Bugtags.start(getAppKey(), SwipBackApplication.this, Bugtags.BTGInvocationEventBubble);
    }

    public abstract String getAppKey();

    /**
     * 获取系统语言
     * @return
     */
    public final boolean isZh() {
        Locale locale   = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh")) {
            return true;
        } else {
            return false;
        }
    }

    private final void initSystem(){
        mSystemManage = new SystemManage(this);
        mSystemManage.registerListener();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (null != mSystemManage) {
            mSystemManage.unregisterListener();
        }
    }

    public final void setSystemChangeListener(SystemChangeListener listener){
        mSystemManage.setSystemChangeListener(listener);
    }

    public final void setNetworkChangeListener(NetworkChangeListener listener){
        mSystemManage.setNetworkChangeListener(listener);
    }

    public int getCurrentPower(){
        return mSystemManage.getCurrentPower();
    }

    public boolean getIsCharging(){
        return mSystemManage.getIsCharging();
    }

}
