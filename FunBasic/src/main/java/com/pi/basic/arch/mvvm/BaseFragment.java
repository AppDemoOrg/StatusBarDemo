package com.pi.basic.arch.mvvm;

import android.support.v4.app.Fragment;

public class BaseFragment<VM extends IViewModel> extends Fragment implements IView<VM> {

    protected VM mViewModel;

    @Override
    public void setViewModel(VM viewModel) {
        this.mViewModel = viewModel;
    }
}
