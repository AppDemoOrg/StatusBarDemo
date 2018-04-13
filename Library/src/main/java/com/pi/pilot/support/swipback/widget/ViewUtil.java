package com.pi.pilot.support.swipback.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

public final class ViewUtil {
    /**
     * 获取DecorView视图
     * @param activity
     * @return
     */
    public static final ViewGroup getDecorView(Activity activity) {
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    /**
     * 获取根视图Drawable
     * @param activity
     * @return
     */
    public static final Drawable getDecorViewDrawable(Activity activity) {
        return getDecorView(activity).getBackground();
    }

    /**
     *  获取内容视图
     * @param activity
     * @return
     */
    public static final View getContentView(Activity activity) {
        return getDecorView(activity).getChildAt(0);
    }

}
