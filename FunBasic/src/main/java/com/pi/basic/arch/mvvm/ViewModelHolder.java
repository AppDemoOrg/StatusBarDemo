package com.pi.basic.arch.mvvm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * @描述：     @ViewModel容器，继承Fragment使得ViewModel可以通过FragmentManager绑定Activity生命周期
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class ViewModelHolder<VM> extends Fragment {

    private VM mViewModel;

    /**
     * 调用{@link #createContainer(Object)}创建实例
     */
    public ViewModelHolder() { }

    public static <M> ViewModelHolder createContainer(@NonNull M viewModel) {
        ViewModelHolder<M> viewModelContainer = new ViewModelHolder<>();
        viewModelContainer.setViewModel(viewModel);
        return viewModelContainer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(@NonNull VM viewModel) {
        mViewModel = viewModel;
    }
}
