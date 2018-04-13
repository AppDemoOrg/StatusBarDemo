package com.pi.pilot.support.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @描述： @自定义使用一张图片做出点击效果
 * @作者： @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class ClickImageView extends AppCompatImageView {

    public ClickImageView(Context context) {
        super(context);
    }

    public ClickImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setBackgroundDrawable(Drawable d) {
        if(null != d){
            ClickBackgroundDrawable layer = new ClickBackgroundDrawable(d);
            super.setBackgroundDrawable(layer);
        }
    }

    protected class ClickBackgroundDrawable extends LayerDrawable {

        protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
        protected int _disabledAlpha = 100;

        public ClickBackgroundDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        @Override
        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;

            for (int state : states) {
                if (state == android.R.attr.state_enabled)
                    enabled = true;
                else if (state == android.R.attr.state_pressed)
                    pressed = true;
            }
            mutate();
            if (enabled && pressed) {
                setColorFilter(_pressedFilter);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(_disabledAlpha);
            } else {
                setColorFilter(null);
            }

            invalidateSelf();

            return super.onStateChange(states);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }
}