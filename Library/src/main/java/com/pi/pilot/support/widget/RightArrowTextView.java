package com.pi.pilot.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.pi.pilot.support.R;


/**
 * @描述： @多国语言左右箭头text
 * @作者： @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class RightArrowTextView extends AppCompatTextView {

    public RightArrowTextView(Context context) {
        this(context, null);
    }

    public RightArrowTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initAttribute(context,attrs);
    }

    public RightArrowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
    }

    private final void initAttribute(Context context, AttributeSet attrs) {
        TypedArray a             = context.obtainStyledAttributes(attrs, R.styleable.RightArrowTextView);
        final Drawable right    = a.getDrawable(R.styleable.RightArrowTextView_rightArrow);
        a.recycle();
        setCompoundDrawablesWithIntrinsicBounds(null,null,right,null);
    }


}
