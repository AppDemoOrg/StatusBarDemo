package com.abt.statusbardemo;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.pi.basic.arch.mvvm.BaseFragment;
import com.pi.pilot.common.base.SwipBackActivity;

/**
 * @描述：     @LauncherActivity
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-04
 */
public class LauncherActivity extends SwipBackActivity<LauncherViewModel, LauncherFragment> {

    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return LauncherFragment.newInstance();
    }

    @NonNull
    @Override
    protected BaseObservable createViewModel() {
        return new LauncherViewModel();
    }

    @Override
    protected boolean enableSwipBack() {
        return false;
    }

}
