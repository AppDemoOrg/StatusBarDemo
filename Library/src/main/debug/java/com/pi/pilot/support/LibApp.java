package com.pi.pilot.support;

import android.app.Application;

import com.pi.pilot.support.swipback.SwipBackConfig;
import com.pi.pilot.support.swipback.SwipBackManager;

/**
 * Created by Administrator on 2017/11/16.
 */

public class LibApp extends Application{

    private static final String TAG = "LibApp";
    @Override
    public void onCreate() {
        super.onCreate();
        initSwipBack();
    }

    private final void initSwipBack(){
        SwipBackManager.initialize(this,new SwipBackConfig.Builder().
                edgeOnly(false).lock(false).rotateScreen(false).create());
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
