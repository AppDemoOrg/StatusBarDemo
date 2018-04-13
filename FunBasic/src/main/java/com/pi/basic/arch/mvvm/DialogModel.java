package com.pi.basic.arch.mvvm;

import android.databinding.BaseObservable;

import java.lang.ref.WeakReference;

public class DialogModel extends BaseObservable {

    private WeakReference<IDialogView> mIDialogView;

    public DialogModel() {

    }

    /**
     * 绑定视图
     * @param dialogView
     */
    void bindView(IDialogView dialogView){
        this.mIDialogView = new WeakReference<IDialogView>(dialogView);
    }

    /**
     * 解除绑定
     */
    void unbindView(){
        this.mIDialogView.clear();
    }

    /**
     * 关闭对话框
     */
    public final void dismiss(){
        if(null != mIDialogView){
            final IDialogView dialog = mIDialogView.get();
            if(null != dialog){
                dialog.hideDialog();
            }
        }
    }
}
