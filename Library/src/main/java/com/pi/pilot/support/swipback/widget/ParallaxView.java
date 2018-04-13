package com.pi.pilot.support.swipback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class ParallaxView extends View {

    private View mParallaxView;

    public ParallaxView(Context context) {
        super(context);
    }

    public void drawParallarView(View parallar) {
        mParallaxView = parallar;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mParallaxView != null) {
            mParallaxView.draw(canvas);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mParallaxView = null;
    }
}
