package com.abt.statusbardemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.IBinder;

import com.pi.basic.concurrent.ThreadPool;
import com.pi.basic.log.LogHelper;
import com.pi.basic.utils.NetworkUtil;
import com.pi.pilot.common.system.NetworkChangeListener;
import com.pi.pilot.common.system.SystemChangeListener;
import com.pi.pilot.common.system.SystemConstant;
import com.pi.pilot.common.system.SystemManage;

import java.util.concurrent.TimeUnit;

/**
 * @描述： @获取系统时间与监听电池电量广播服务
 * @作者： @蒋诗朋
 * @创建时间： @2017-12-01
 */
public class SystemService extends Service {
    private static final String TAG = SystemService.class.getSimpleName();
    private static SystemChangeListener sSystemChangeListener;
    private static NetworkChangeListener sNetworkChangeListener;
    private static int sCurrentPowerProgress;
    private static boolean sIsCharging;

    public static final void registerSystemChangeListener(SystemChangeListener listener) {
        sSystemChangeListener = listener;
    }

    public static final void registerNetworkChangeListener(NetworkChangeListener listener){
        sNetworkChangeListener = listener;
    }

    public static final void unregisterSystemChangeListener(){
        sSystemChangeListener = null;
    }

    public static final void unregisterNetworkChangeListener(){
        sNetworkChangeListener = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerBatteryReceiver();
        registerNetworkChangedReceiver();
        startTimer();
        initBattery();
    }

    /**
     * 开机获取最初的电池状态
     */
    private void initBattery() {
        sIsCharging = getCharging();
    }

    private final void startTimer() {
        ThreadPool.Builder.schedule(1).setImmediatelyShutdown(true).scheduleBuilder().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //LogHelper.d(TAG, "sSystemChangeListener " + sSystemChangeListener);
                if (null != sSystemChangeListener) {
                    final long time = System.currentTimeMillis();
                    notifyTimeChanged(time);
                    //sSystemChangeListener.onTimeChanged(time);
                }
            }
        },0,1, TimeUnit.SECONDS/*TimeUnit.MINUTES*/);
    }

    private final void notifyTimeChanged(long time){
        final Intent intent = new Intent();
        intent.setAction(SystemManage.ACTION_TIME_CHANGE);
        intent.putExtra(SystemConstant.TIME,time);
        getApplication().sendBroadcast(intent);
    }

    private final void notifyBatteryChanged(boolean isCharging, int progress ){
        final Intent intent = new Intent();
        intent.setAction(SystemManage.ACTION_BATTERY_CHANGE);
        intent.putExtra(SystemConstant.IS_CHARGING,isCharging);
        intent.putExtra(SystemConstant.PROGRESS,progress);
        getApplication().sendBroadcast(intent);
    }

    private final void notifyNetworkChanged(){
        final Intent intent = new Intent();
        intent.setAction(SystemManage.ACTION_NETWORK_CHANGE);
        getApplication().sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mBatteryChangedReceiver);
        super.onDestroy();
    }

    private final void registerBatteryReceiver() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(mBatteryChangedReceiver, intentFilter);
    }

    private final void registerNetworkChangedReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkBroadcastReceiver(), filter);
    }

    private BroadcastReceiver mBatteryChangedReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            int progress = 0;
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                progress = level * 100 / scale;
                sCurrentPowerProgress = progress;
                LogHelper.d(TAG, "ACTION_BATTERY_CHANGED");
            } else if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
                sIsCharging = true;
                LogHelper.d(TAG, "ACTION_POWER_CONNECTED");
            } else if(Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
                sIsCharging = false;
                LogHelper.d(TAG, "ACTION_POWER_DISCONNECTED");
            }

            if (null != sSystemChangeListener) {
                notifyBatteryChanged(sIsCharging, progress);
                sSystemChangeListener.onBatteryChanged(sIsCharging, progress);
                LogHelper.d(TAG, "onBatteryChanged isCharging = "+sIsCharging);
                LogHelper.d(TAG, "onBatteryChanged progress = "+progress);
            }
        }
    };

    private class NetworkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogHelper.d(TAG, "onReceive network action ");
            // 如果相等的话就说明网络状态发生了变化
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if(null != sNetworkChangeListener){
                    notifyNetworkChanged();
                    sNetworkChangeListener.onNetworkChanged();
                    LogHelper.d(TAG, "onReceive network isAvailable ");
                    return;
                }
            }

            //飞行模式直接弹出对话框
            if(NetworkUtil.isAirplaneMode()){
                if(null != sNetworkChangeListener){
                    notifyNetworkChanged();
                    sNetworkChangeListener.onNetworkChanged();
                    LogHelper.d(TAG, "onReceive network isAirplaneMode ");
                    return;
                }
            }
        }
    }

    public final static int getCurrentPower() {
        return sCurrentPowerProgress;
    }

    public final static boolean isCharging() {
        return sIsCharging;
    }

    public final boolean getCharging() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, filter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) ||
                (status == BatteryManager.BATTERY_STATUS_FULL);
        return isCharging;
    }

}
