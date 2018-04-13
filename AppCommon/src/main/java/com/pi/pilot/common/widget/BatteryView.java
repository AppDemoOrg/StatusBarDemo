package com.pi.pilot.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.pi.basic.utils.ResourceUtil;
import com.pi.pilot.common.R;

public class BatteryView extends View {

	private int mProgress = 50;
	private int mPowerLeftPadding;
	private int mPowerRightPadding;
	private int mPowerWidth;

	private Bitmap mBatteryBackground;
	private Bitmap mBatteryLevel;
	private Bitmap mBatteryCharge;
	private boolean mIsCharge 	    = false;

	public BatteryView(Context context) {
		this(context, null);
	}

	public BatteryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array                   = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);
		Drawable drawable 				   = array.getDrawable(R.styleable.BatteryView_batteryBackground);
		BitmapDrawable bitmapDrawable	   = null;
		if (null != drawable) {
			bitmapDrawable 	    		   = (BitmapDrawable) drawable;
			mBatteryBackground 		       = bitmapDrawable.getBitmap();
		}

		drawable 						   = array.getDrawable(R.styleable.BatteryView_batteryLevel);
		if(null != drawable){
			bitmapDrawable 	    		   = (BitmapDrawable) drawable;
			mBatteryLevel 				   = bitmapDrawable.getBitmap();
		}

		drawable 						   = array.getDrawable(R.styleable.BatteryView_batteryChargeIcon);
		if(null != drawable){
			bitmapDrawable 	    		   = (BitmapDrawable) drawable;
			mBatteryCharge 			       = bitmapDrawable.getBitmap();
		}

		array.recycle();
		mPowerLeftPadding = ResourceUtil.getDimensionPixelOffset(R.dimen.dp_2);
		mPowerRightPadding = ResourceUtil.getDimensionPixelOffset(R.dimen.dp_2);
		mPowerWidth = mBatteryBackground.getWidth() - mPowerLeftPadding - mPowerRightPadding;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (!mIsCharge) {
			if (null != mBatteryBackground && null != mBatteryLevel) {
				canvas.drawBitmap(mBatteryBackground, 0, 0, null);
				canvas.save();
				canvas.clipRect(0, 0, calculateClipRight(mProgress), getHeight());
				/*if (mProgress<20) {
					Paint paint = new Paint();
					paint.setColor(Color.RED);
					canvas.drawPaint(paint);
				} else {
					Paint paint = new Paint();
					paint.setColor(Color.BLACK);
					canvas.drawPaint(paint);
				}*/
				canvas.drawBitmap(mBatteryLevel, 0, 0, null);
				canvas.restore();
			}
		} else {
			canvas.drawBitmap(mBatteryCharge, 0, 0, null);
			canvas.save();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = widthSize;
		int height = heightSize;
		if (MeasureSpec.AT_MOST == widthMode) {
			width = mBatteryBackground.getWidth();
		}
		if (MeasureSpec.AT_MOST == heightMode) {
			height = mBatteryBackground.getHeight();
		}
		setMeasuredDimension(width, height);
	}

	/**
	 * @param mProgress
	 * between 0 and 100
	 */
	public void setProgress(int mProgress) {
		if (mProgress < 0 || mProgress > 100) {
			return;
		}
		this.mProgress = mProgress;
		postInvalidate();
	}

	public final void setBatteryCharge(boolean isCharge){
		mIsCharge = isCharge;
		postInvalidate();
	}

	private int calculateClipRight(int progress) {
		return mPowerWidth * progress / 100 + mPowerLeftPadding;
	}
}
