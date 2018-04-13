package com.abt.statusbardemo;

import android.databinding.BaseObservable;

import com.pi.basic.arch.mvvm.INavigator;
import com.pi.basic.arch.mvvm.IViewModel;

import java.lang.ref.WeakReference;

/**
 * @描述： @LauncherViewModel
 * @作者： @黄卫旗
 * @创建时间： @2018-04-08
 */
public class LauncherViewModel extends BaseObservable implements IViewModel {

    private WeakReference<LauncherContract.ILauncher> mPreviewView;

    /**
     * 关联视图
     * @param previewView
     */
    public final void setPreviewView(LauncherContract.ILauncher previewView) {
        mPreviewView = new WeakReference<LauncherContract.ILauncher>(previewView);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void setNavigator(INavigator navigator) {

    }

    public void clickCamera() {
        if (null != mPreviewView) {
            mPreviewView.get().clickCamera();
        }
    }

    public void clickGallery() {
        if (null != mPreviewView) {
            mPreviewView.get().clickGallery();
        }
    }

    public void clickLive() {
        if (null != mPreviewView) {
            mPreviewView.get().clickLive();
        }
    }

    public void clickSetting() {
        if (null != mPreviewView) {
            mPreviewView.get().clickSetting();
        }
    }

}
