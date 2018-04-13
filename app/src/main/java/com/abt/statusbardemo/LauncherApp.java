package com.abt.statusbardemo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.abt.statusbardemo.service.SystemService;
import com.pi.basic.log.LogHelper;
import com.pi.pilot.common.base.SwipBackApplication;
import com.pi.pilot.common.config.PathConfig;
import com.pi.pilot.common.system.SystemConstant;

/**
 * @描述：     @LauncherApp
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-04
 */
public class LauncherApp extends SwipBackApplication {

    private static final String TAG = LauncherApp.class.getSimpleName();
    public static Context mApplicationContext;
    private static LauncherApp mLauncherApp;

    public static LauncherApp getInstance() {
        return mLauncherApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLauncherApp = this;
        startService();
        initCommonModule();
    }

    private final void startService() {
        Intent intent = new Intent(this, SystemService.class);
        this.startService(intent);
    }

    // 初始化公共模块
    private final void initCommonModule() {
        LogHelper.d(TAG, "initCommonModule()");
    }

    public final int getCurrentPower(){
        Intent intent = this.registerReceiver( null ,
                new IntentFilter( Intent.ACTION_BATTERY_CHANGED ) ) ;
        return intent.getIntExtra( SystemConstant.LEVEL , 0 );//电量（0-100）
    }

    public final boolean getIsCharging(){
        Intent intent = this.registerReceiver( null ,
                new IntentFilter( Intent.ACTION_BATTERY_CHANGED ) ) ;
        return intent.getBooleanExtra( SystemConstant.IS_CHARGING , false );//电量（0-100）
    }

    @Override
    public void executeAsycInit() {
        mApplicationContext = this;
        PathConfig.makedirs();
    }

    @Override
    public String getAppKey() {
        //return "814bc2606920e02f2d144181d92eb131";
        return "";
    }

}
