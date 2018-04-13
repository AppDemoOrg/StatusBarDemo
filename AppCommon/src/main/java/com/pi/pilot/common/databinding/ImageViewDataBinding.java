package com.pi.pilot.common.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.pi.basic.loader.ImageLoader;
import com.pi.basic.log.LogHelper;

/**
 * @描述：     @自定义绑定属性
 * @作者：     @蒋诗朋
 * @创建时间： @2018-02-03
 */
public class ImageViewDataBinding {

    @BindingAdapter("ir")
    public static final void setImageResource(ImageView imageView, int ir) {
        imageView.setImageResource(ir);
    }

    @BindingAdapter("br")
    public static final void setBackgroundResource(ImageView imageView, int br){
        imageView.setBackgroundResource(br);
    }

    @BindingAdapter("bitmap")
    public static final void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("filepath")
    public static final void setImageLocal(ImageView imageView, String filepath) {
        LogHelper.e("ImageViewDataBinding", filepath+"");
        ImageLoader.into(imageView).loadImage(filepath);
    }

}
