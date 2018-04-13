package com.pi.basic.app;

import android.app.Application;

import com.pi.basic.BuildConfig;
import com.pi.basic.debug.DebugManage;

public abstract class BasicApplication extends Application{

    private static BasicApplication sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        init();
        initComplete();
    }

    public static final BasicApplication getAppContext(){
        return sContext;
    }

    private final void init(){
        if(BuildConfig.DEBUG){
            DebugManage.initialize(this);
        }
    }

    public abstract void initComplete();

}
