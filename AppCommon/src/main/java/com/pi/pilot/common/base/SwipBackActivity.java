package com.pi.pilot.common.base;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.pi.basic.arch.mvvm.BaseActivity;
import com.pi.basic.arch.mvvm.BaseFragment;
import com.pi.basic.arch.mvvm.IViewModel;
import com.pi.basic.log.LogHelper;
import com.pi.pilot.common.R;
import com.pi.pilot.common.model.ToolbarViewModel;
import com.pi.pilot.support.swipback.SwipBackManager;

/**
 * @描述： @app activity基类
 * @作者： @蒋诗朋
 * @创建时间： @2018-02-01
 */
public abstract class SwipBackActivity<VM extends BaseObservable & IViewModel, V extends BaseFragment<VM>>
        extends BaseActivity {

    private final int LEFT_KEY_CODE = KeyEvent.KEYCODE_CAMERA;
    private final int RIGHT_KEY_CODE = KeyEvent.KEYCODE_BACK;

    private CountDown mCountDown;
    private boolean mShortPress = false;
    private volatile boolean mCounting = false;
    private ToolbarViewModel mToolbarViewModel;
    protected SwipBackApplication mApp;
    private TextView mTvTitle;
    private TextView mTvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSwipBack()) SwipBackManager.addSwipBackList(this);
        mToolbarViewModel = new ToolbarViewModel();
        mApp = (SwipBackApplication) getApplication();
        mApp.setSystemChangeListener(mToolbarViewModel);
        mApp.setNetworkChangeListener(mToolbarViewModel);
        mToolbarViewModel.setProgress(mApp.getCurrentPower());
        mToolbarViewModel.setIsCharge(mApp.getIsCharging());

        mTvTitle = findViewById(R.id.tv_title);
        mTvTime = findViewById(R.id.tv_time);
        mToolbarViewModel.setTvTitle(mTvTitle);
        mToolbarViewModel.setTvTime(mTvTime);
    }

    public final void setTitle(int resId) {
        mTvTitle = findViewById(R.id.tv_title);
        mTvTime = findViewById(R.id.tv_time);
        mTvTitle.setText(resId);

        ViewTreeObserver vto = mTvTitle.getViewTreeObserver();
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
        });
        //mBatteryView.setProgress(mCameraApp.getCurrentPower());
        //mBatteryView.setBatteryCharge(mCameraApp.getIsCharging());
        //mTvTime.setText(DateUtil.dateToString(new Date(), DateStyle.HH_MM));
    }

    public ToolbarViewModel getToolbarViewModel() {
        return mToolbarViewModel;
    }

    protected boolean enablelongPressAction() {
        return false;
    }

    /**
     * 是否启动滑动删除
     */
    protected boolean enableSwipBack() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogHelper.d(TAG, "onKeyDown() keyCode = " + keyCode);
        if (keyCode == RIGHT_KEY_CODE || keyCode == LEFT_KEY_CODE) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                listenLongPress();
                listenShortPress(event);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 监听长按事件
     */
    private void listenLongPress() {
        if (null != mCountDown && !mCounting) {
            mCountDown.cancel();
            mCountDown = null;
        } else if (mCountDown == null && !mCounting) {
            mCountDown = new CountDown(5 * 1000, 1000);
            mCountDown.start();
            mCounting = true;
        }
    }

    /**
     * 监听短按事件
     */
    private void listenShortPress(KeyEvent event) {
        event.startTracking();
        if (event.getRepeatCount() == 0) {
            mShortPress = true;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == RIGHT_KEY_CODE || keyCode == LEFT_KEY_CODE) {
            if (mShortPress) { // 处理短按事件
                handleShortPress();
            } else { // 处理长按事件，这里什么都不用做，
                // 等长按5秒后在线程中跳转到Launcher
                // Don't handle long press here
            }
            mShortPress = false;
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 处理短按事件
     */
    private void handleShortPress() {
        if (null != mCountDown && mCounting) {
            mCountDown.cancel();
            mCountDown = null;
            mCounting = false;
            LogHelper.d(TAG, "Count Down Cancel()");
        }
        // Toast.makeText(this, "mShortPress", Toast.LENGTH_LONG).show();
    }

    /**
     * 处理长按返回Launcher倒计时
     */
    private class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            LogHelper.d(TAG, "CountDown millisInFuture = " + millisInFuture);
            LogHelper.d(TAG, "CountDown countDownInterval = " + countDownInterval);
        }

        @Override
        public void onTick(long l) {
            LogHelper.d(TAG, "onTick l = " + l);
        }

        @Override
        public void onFinish() {
            if (enablelongPressAction()) {
                onLongPressAction();
            } else {
                jumpToLauncher();
                jumpToLauncherAction();
            }
        }
    }

    protected void onLongPressAction(){};
    protected void jumpToLauncherAction(){};

    protected void jumpToLauncher() {
        LogHelper.d(TAG, "onFinish()");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        SwipBackActivity.this.startActivity(intent);
        // SwipBackActivity.this.finish();
        LogHelper.d(TAG, "SwipBackActivity.this.finish()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.setSystemChangeListener(null);
    }

}
