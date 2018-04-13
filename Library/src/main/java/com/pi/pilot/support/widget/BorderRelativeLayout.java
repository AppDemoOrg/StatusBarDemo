package com.pi.pilot.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.pi.pilot.support.R;


/**
 * @描述： @自定义分割线
 * @作者： @蒋诗朋
 * @创建时间： @2017-04-25
 */
public class BorderRelativeLayout extends RelativeLayout {
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 边框颜色
     */
    private int mPaintColor;
    /**
     * 边框粗细
     */
    private float mBorderStrokeWidth;
    /**
     * 底边边线左边断开距离
     */
    private int mBorderBottomLeftMargin;
    /**
     * 底边边线右边断开距离
     */
    private int mBorderBottomRightMargin;
    /**
     * 是否需要上边框
     */
    private boolean mTopBorderVisible;
    /**
     * 是否需要左右边框
     */
    private boolean mLeftAndRightBorderVisible;
    /**
     * 是否需要下边框
     */
    private boolean mBottomBorderVisible;

    public BorderRelativeLayout(Context context) {
        this(context, null);
    }

    public BorderRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取自定义属性
        TypedArray ta                   = context.obtainStyledAttributes(attrs, R.styleable.BorderRelativeLayout);
        mPaintColor                    = ta.getColor(R.styleable.BorderRelativeLayout_borderColor, Color.GRAY);
        mBorderStrokeWidth            = ta.getDimensionPixelSize(R.styleable.BorderRelativeLayout_borderStrokeWidth, 0);
        mBorderBottomLeftMargin      = ta.getDimensionPixelSize(R.styleable.BorderRelativeLayout_borderBottomLeftMargin, 0);
        mBorderBottomRightMargin     = ta.getDimensionPixelSize(R.styleable.BorderRelativeLayout_borderBottomRightMargin, 0);
        mTopBorderVisible = ta.getBoolean(R.styleable.BorderRelativeLayout_topBorderVisible, true);
        mLeftAndRightBorderVisible = ta.getBoolean(R.styleable.BorderRelativeLayout_leftAndRightBorderVisible, false);
        mBottomBorderVisible = ta.getBoolean(R.styleable.BorderRelativeLayout_bottomBorderVisible, true);
        ta.recycle();
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorderStrokeWidth);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //  画4个边
        if (mTopBorderVisible) {
            canvas.drawLine(0, 0, this.getWidth(), 0, mPaint);
        }

        if (mBottomBorderVisible) {
            canvas.drawLine(mBorderBottomLeftMargin, this.getHeight(), this.getWidth() -
                    mBorderBottomRightMargin, this.getHeight(), mPaint);
        }

        if (mLeftAndRightBorderVisible) {
            canvas.drawLine(0, 0, 0, this.getHeight(), mPaint);
            canvas.drawLine(this.getWidth(), 0, this.getWidth(), this.getHeight(), mPaint);
        }

    }

    /**
     * 设置边框颜色
     *
     * @param color
     */
    public void setBorderColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置边框宽度
     *
     * @param size
     */
    public void setBorderStrokeWidth(float size) {
        mPaint.setStrokeWidth(size);
        invalidate();
    }


    /**
     * 设置是否需要顶部边框
     * @param topBorderVisible
     */
    public void setTopBorderVisible(boolean topBorderVisible) {
        mTopBorderVisible = topBorderVisible;
        invalidate();
    }

    /**
     * 设置是否需要底部边框
     * @param bottomBorderVisible
     */
    public void setBottomBorderVisible(boolean bottomBorderVisible) {
        mBottomBorderVisible = bottomBorderVisible;
        invalidate();
    }
}