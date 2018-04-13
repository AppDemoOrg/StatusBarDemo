package com.pi.basic.arch.mvvm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public  abstract class BaseDialog<VM extends DialogModel> extends DialogFragment implements IDialogView{
    public static final  String TAG = "DIALOG_FRAGMENT";

    private int mLayoutId;
    private int mVariableId;
    private VM  mViewModel;

    @SuppressLint("ValidFragment")
    public BaseDialog(int style,int theme,int layoutId, int variableId, VM model) {
        setStyle(style, theme);
        mLayoutId   = layoutId;
        mVariableId = variableId;
        mViewModel  = model;
        mViewModel.bindView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final ViewDataBinding binding   = DataBindingUtil.inflate(inflater,
                mLayoutId, container, false);
        binding.setVariable(mVariableId,mViewModel);
        return binding.getRoot();
    }

    /**
     *  获取VM
     * @return
     */
    public final  VM getViewModel(){
        return mViewModel;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mViewModel.unbindView();
        super.onDestroyView();
    }


    protected void init(Bundle savedInstanceState){

    }

    /**
     * 显示对话框
     */
    public final void show(FragmentManager fragmentManager){
        try {
            show(fragmentManager,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideDialog() {
        dismiss();
    }
}
