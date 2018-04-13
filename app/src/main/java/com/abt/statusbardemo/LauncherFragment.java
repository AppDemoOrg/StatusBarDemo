package com.abt.statusbardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abt.statusbardemo.databinding.FragmentLauncherBinding;
import com.abt.statusbardemo.service.SystemService;
import com.pi.basic.arch.mvvm.BaseFragment;
import com.pi.pilot.common.base.SwipBackActivity;
import com.pi.pilot.common.global.GlobalConstant;
import com.pi.pilot.common.model.ToolbarViewModel;

/**
 * @描述： @LauncherFragment
 * @作者： @黄卫旗
 * @创建时间： @2018-04-08
 */
public class LauncherFragment extends BaseFragment<LauncherViewModel>
        implements LauncherContract.ILauncher {

    private FragmentLauncherBinding mPreviewBinding;
    private ToolbarViewModel mToolbarViewModel;
    /**
     * 返回实例
     */
    public static LauncherFragment newInstance() {
        return new LauncherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mToolbarViewModel = ((SwipBackActivity)getActivity()).getToolbarViewModel();
        SystemService.registerSystemChangeListener(mToolbarViewModel);
        SystemService.registerNetworkChangeListener(mToolbarViewModel);

        mPreviewBinding = FragmentLauncherBinding.inflate(
                inflater, container, false);
        mPreviewBinding.setModel(mViewModel);
        mPreviewBinding.setToolbar(mToolbarViewModel);
        mViewModel.setPreviewView(this);
        mViewModel.initialize();
        return mPreviewBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SystemService.unregisterSystemChangeListener();
        SystemService.unregisterNetworkChangeListener();
    }

    @Override
    public void clickCamera() {
        Intent camera = getActivity().getPackageManager().getLaunchIntentForPackage(
                GlobalConstant.CAMERA_APP_PACKAGE_NAME);
        if (null != camera) startActivity(camera);
        getActivity().overridePendingTransition(R.anim.slide_in_right, 0);
    }

    @Override
    public void clickGallery() {
        Intent gallery = getActivity().getPackageManager().getLaunchIntentForPackage(
                GlobalConstant.GALLERY_APP_PACKAGE_NAME);
        if (null != gallery) startActivity(gallery);
        getActivity().overridePendingTransition(R.anim.slide_in_right,0);
    }

    @Override
    public void clickLive() {
        Intent live = getActivity().getPackageManager().getLaunchIntentForPackage(
                GlobalConstant.LIVE_APP_PACKAGE_NAME);
        if (null != live) startActivity(live);
        getActivity().overridePendingTransition(R.anim.slide_in_right,0);
    }

    @Override
    public void clickSetting() {
        Intent setting = getActivity().getPackageManager().getLaunchIntentForPackage(
                GlobalConstant.SETTING_APP_PACKAGE_NAME);
        if (null != setting) startActivity(setting);
        getActivity().overridePendingTransition(R.anim.slide_in_right,0);
    }
}
