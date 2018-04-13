package com.pi.pilot.common.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.pi.basic.log.LogHelper;

/**
 * @描述：     @系统管理
 * @作者：     @蒋诗朋
 * @创建时间： @2017-12-01
 */
public final class SystemManage {
    private static final String TAG = "SystemManage";

    public static final String ACTION_BATTERY_CHANGE  = "com.pi.system.battery.change";
    public static final String ACTION_TIME_CHANGE      = "com.pi.system.time.change";
    public static final String ACTION_NETWORK_CHANGE  = "com.pi.system.network.change";

    private final SystemReceiver mSystemReceiver;
    private Context mContext;

    private SystemChangeListener  mSystemChangeListener;
    private NetworkChangeListener mNetworkChangeListener;

    public SystemManage(Context context){
        mContext         = context;
        mSystemReceiver = new SystemReceiver();
    }

    public final void registerListener(){
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_BATTERY_CHANGE);
        filter.addAction(ACTION_TIME_CHANGE);
        filter.addAction(ACTION_NETWORK_CHANGE);
        mContext.registerReceiver(mSystemReceiver,filter);
    }

    public final int getCurrentPower(){
        Intent intent = mContext.registerReceiver( null ,
                        new IntentFilter( Intent.ACTION_BATTERY_CHANGED ) ) ;
        return intent.getIntExtra( SystemConstant.LEVEL , 0 );//电量（0-100）
    }

    // 没有效果，暂时注掉
    /*public final boolean getIsCharging() {
        Intent intent = mContext.registerReceiver(null ,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return intent.getBooleanExtra(SystemConstant.IS_CHARGING, false);//电量（0-100）
    }*/

    public final boolean getIsCharging() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = mContext.registerReceiver(null, filter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) ||
                (status == BatteryManager.BATTERY_STATUS_FULL);
        return isCharging;
    }

    public final void unregisterListener(){
        if(null != mSystemReceiver){
            mContext.unregisterReceiver(mSystemReceiver);
        }
    }

    public final void setSystemChangeListener(SystemChangeListener listener){
        mSystemChangeListener = listener;
    }

    public final void setNetworkChangeListener(NetworkChangeListener listener){
        mNetworkChangeListener = listener;
    }

    private final class SystemReceiver extends BroadcastReceiver{
        public SystemReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //电池电量
            if(ACTION_BATTERY_CHANGE.equals(action)){
                LogHelper.v(TAG,"battery change is coming 01");
                if(null != mSystemChangeListener){
                    final boolean isCharging  = intent.getBooleanExtra(
                            SystemConstant.IS_CHARGING,false);
                    final int progress         = intent.getIntExtra(
                            SystemConstant.PROGRESS,0);
                    mSystemChangeListener.onBatteryChanged(isCharging,progress);
                }
            }else if(ACTION_TIME_CHANGE.equals(action)){ //时间
                //LogHelper.v(TAG,"time change is coming 01");
                if(null != mSystemChangeListener){
                    final long time  = intent.getLongExtra(
                            SystemConstant.TIME,System.currentTimeMillis());
                    //LogHelper.v(TAG,"time change is coming 02 --- >" + time);
                    mSystemChangeListener.onTimeChanged(time);
                }
            }else if(ACTION_NETWORK_CHANGE.equals(action)){//网络
                LogHelper.v(TAG,"network change is coming 01");
                if(null != mNetworkChangeListener){
                    mNetworkChangeListener.onNetworkChanged();
                }
            }
        }
    }

}
