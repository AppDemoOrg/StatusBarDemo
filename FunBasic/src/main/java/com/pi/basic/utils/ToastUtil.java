package com.pi.basic.utils;

import android.widget.Toast;

import com.pi.basic.app.BasicApplication;


/**
 * @描述：     @安全显示吐司
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class ToastUtil {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG  = Toast.LENGTH_LONG;

    private static Toast sToast;

    /**
     * 显示吐司
     * @param resId
     */
    public static final void showToast(int resId){
        if(null == sToast) {
            sToast = Toast.makeText(
                    BasicApplication.getAppContext(),resId, LENGTH_SHORT);
            // PS: 后面的偏移量针对前面的Gravity来设置的
        }else{
            sToast.setText(resId);
        }

        sToast.show();
    }

    /**
     * 显示吐司
     * @param msg
     */
    public static final void showToast(String msg){
        if(null == sToast) {
            sToast = Toast.makeText(BasicApplication.getAppContext()
                    ,msg, LENGTH_SHORT);
        }else{
            sToast.setText(msg);
        }
        sToast.show();
    }


}
