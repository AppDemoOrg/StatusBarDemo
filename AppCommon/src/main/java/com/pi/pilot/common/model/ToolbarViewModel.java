package com.pi.pilot.common.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.pi.basic.log.LogHelper;
import com.pi.basic.utils.DateStyle;
import com.pi.basic.utils.DateUtil;
import com.pi.basic.utils.NetworkUtil;
import com.pi.basic.utils.ResourceUtil;
import com.pi.pilot.common.R;
import com.pi.pilot.common.system.NetworkChangeListener;
import com.pi.pilot.common.system.SystemChangeListener;
import com.pi.pilot.common.util.TimeSettingUtil;

/**
 * @描述： @状态栏VM
 * @作者： @蒋诗朋
 * @创建时间： @2017-12-01
 */
public final class ToolbarViewModel extends BaseObservable
        implements SystemChangeListener, NetworkChangeListener,
        ViewTreeObserver.OnPreDrawListener  {

    private static final String TAG = ToolbarViewModel.class.getSimpleName();
    private TextView mTvTitle;
    private TextView mTvTime;

    /**
     * 显示网络状态
     */
    public final ObservableField<Integer>    network        = new ObservableField<>();
    /**
     * 显示标题
     */
    public final ObservableField<String>     title          = new ObservableField<>();
    /**
     * 显示时间
     */
    public final ObservableField<String>     time           = new ObservableField<>();
    /**
     * 电量
     */
    public final ObservableField<Integer>    progress       = new ObservableField<>();
    /**
     * 是否正在充电
     */
    public final ObservableField<Boolean>    isCharge       = new ObservableField<>();

    public ToolbarViewModel() {
        init();
    }

    public ToolbarViewModel(int title) {
        this.title.set(ResourceUtil.getString(title));
        init();
    }

    private void init() {
        LogHelper.d(TAG, "init");
        // 初始化时间
        // 初始化电池状态
        // 初始化网络
        LogHelper.d(TAG, "init->network.get() = "+network.get());
    }

    public void setTitle(String str) {
        title.set(str);
    }

    public void setIsCharge(boolean charge) {
        isCharge.set(charge);
    }

    public void setProgress(int pro) {
        progress.set(pro);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeChanged(long now) {
        //LogHelper.d(TAG, "onTimeChanged now = "+now);
        if (TimeSettingUtil.is24HourSystem()) {
            time.set(DateUtil.longToString(now, DateStyle.HH_MM.getValue()));
        } else {
            time.set(TimeSettingUtil.getHourAndMinuteFromTime(now));
        }
    }

    @Override
    public void onBatteryChanged(boolean isCharging, int pro) {
        LogHelper.d(TAG, "onBatteryChanged");
        progress.set(pro);
        isCharge.set(isCharging);
    }

    @Override
    public void onNetworkChanged() {
        LogHelper.d(TAG, "onNetworkChanged");
        switch (NetworkUtil.getNetWorkType()) {
            case NetworkUtil.NETWORK_WIFI:
                int signal = NetworkUtil.getWifiSignal();
                LogHelper.d(TAG, "onNetworkChanged signal = "+signal);
                if (signal == 1) {
                    network.set(R.drawable.black_wifi_3);
                } else if (signal == 2 || signal == 3) {
                    network.set(R.drawable.black_wifi_2);
                } else if (signal == 4) {
                    network.set(R.drawable.black_wifi_1);
                } else {
                    network.set(null);
                }
                break;
            case NetworkUtil.NETWORK_4G:
                network.set(R.drawable.g4);
                break;
            case NetworkUtil.NETWORK_3G:
                network.set(R.drawable.g3);
                break;
            case NetworkUtil.NETWORK_2G:
            case NetworkUtil.NETWORK_UNKNOWN:
            case NetworkUtil.NETWORK_NO:
                network.set(null);
                break;
        }
    }


/*    ViewTreeObserver vto = mTvTitle.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        public boolean onPreDraw() {
            int height = mTvTitle.getMeasuredHeight();
            int width = mTvTitle.getMeasuredWidth();
            int line = mTvTitle.getLineCount();
            Log.d(TAG, "onPreDraw height = "+height);
            Log.d(TAG, "onPreDraw width = "+width);
            Log.d(TAG, "onPreDraw line = "+line);
            if (width >= 182) {//91DP
                mTvTime.setVisibility(View.GONE);
            } else {
                mTvTime.setVisibility(View.VISIBLE);
            }
            //得到属性实现自己的操作
            return true;
        }
    }*/

    public void setTvTitle(TextView title) {
        mTvTitle = title;
    }

    public void setTvTime(TextView time) {
        mTvTime = time;
    }

    @Override
    public boolean onPreDraw() {
        if (mTvTitle == null) return true;
        int height = mTvTitle.getMeasuredHeight();
        int width = mTvTitle.getMeasuredWidth();
        int line = mTvTitle.getLineCount();
        LogHelper.d(TAG, "onPreDraw height = "+height);
        LogHelper.d(TAG, "onPreDraw width = "+width);
        LogHelper.d(TAG, "onPreDraw line = "+line);
        if (width >= 182) {//91DP
            mTvTime.setVisibility(View.GONE);
        } else {
            mTvTime.setVisibility(View.VISIBLE);
        }
        //得到属性实现自己的操作
        return true;
    };

}
