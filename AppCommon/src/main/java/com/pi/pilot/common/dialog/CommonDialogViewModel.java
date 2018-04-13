package com.pi.pilot.common.dialog;

import com.pi.basic.arch.mvvm.DialogModel;

/**
 * @描述： @业务层对话框VM
 * @作者： @蒋诗朋
 * @创建时间： @2017-12-01
 */
public class CommonDialogViewModel extends DialogModel{

    public String   positiveName;
    public String   negativeName;
    public String   neutralName;
    public String   content;

    private CommonDialog.DialogListener mDialogListener;

    public CommonDialogViewModel(String positiveName,
                                 String negativeName,
                                 String neutralName,
                                 String content,
                                 CommonDialog.DialogListener dialogListener) {
        this.positiveName    = positiveName;
        this.negativeName    = negativeName;
        this.neutralName     = neutralName;
        this.content          = content;
        this.mDialogListener = dialogListener;
    }

    public final void onPositiveClick(){
        if(null != mDialogListener){
            mDialogListener.onPositive(this);
        }
    }

    public final void onNegativeClick(){
        if(null != mDialogListener){
            mDialogListener.onNegative(this);
        }
    }

    public final void onNeutralClick(){
        if(null != mDialogListener){
            mDialogListener.onNeutral(this);
        }
    }

}
