package com.pi.pilot.common.databinding;

import android.databinding.BindingAdapter;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * @描述：     @自定义绑定属性
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-12
 */
public class TextViewDataBinding {

    @BindingAdapter("pd")
    public static final void setOnPreDraw(TextView textView, ViewTreeObserver.OnPreDrawListener pd) {
        ViewTreeObserver vto = textView.getViewTreeObserver();
        vto.addOnPreDrawListener(pd);
    }

}
