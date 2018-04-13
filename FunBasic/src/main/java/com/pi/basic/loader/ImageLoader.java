package com.pi.basic.loader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.pi.basic.loader.transform.CircleTransformation;

import java.lang.ref.WeakReference;

/**
 * @描述：     @图片、视频缩略图加载封装库
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
public final class ImageLoader {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FILE = "file://";
    private static final String SEPARATOR = "/";
    private static final String HTTP = "http";

    private WeakReference<ImageView> mImageView;
    private Object mImageUrlObj;
    private long mTotalBytes    = 0;
    private long mLastBytesRead = 0;
    private boolean mLastStatus = false;
    private Handler mMainThreadHandler;


    public static ImageLoader into(ImageView imageView) {
        return new ImageLoader(imageView);
    }

    private ImageLoader(ImageView imageView) {
        mImageView          = new WeakReference<>(imageView);
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取绑定视图
     * @return
     */
    public final ImageView getImageView() {
        if (mImageView != null) {
            return mImageView.get();
        }
        return null;
    }

    /**
     * 获取当前Context
     * @return
     */
    public final Context getContext() {
        if (getImageView() != null) {
            return getImageView().getContext();
        }
        return null;
    }

    /**
     * 获取url
     * @return
     */
    public final String getImageUrl() {
        if (mImageUrlObj == null ||
                !(mImageUrlObj instanceof String)) {
            return null;
        }
        return (String) mImageUrlObj;
    }

    /**
     * resid转化Uri
     * @param resourceId
     * @return
     */
    public final Uri resId2Uri(int resourceId) {
        if (getContext() == null) return null;
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + SEPARATOR + resourceId);
    }

    /**
     * 加载本地资源文件
     * @param resId
     * @param options
     */
    public final void load(int resId, RequestOptions options) {
        load(resId2Uri(resId), options);
    }

    /**
     *  加载Uri
     * @param uri
     * @param options
     */
    public final void load(Uri uri, RequestOptions options) {
        if (uri == null || getContext() == null) return;
        requestBuilder(uri, options).into(getImageView());
    }

    /**
     * 加载本地url
     * @param url
     * @param options
     */
    public final void load(String url, RequestOptions options) {
        if (url == null || getContext() == null) return;
        requestBuilder(url, options).into(getImageView());
    }

    private final RequestBuilder<Drawable> requestBuilder(Object obj, RequestOptions options) {
        this.mImageUrlObj = obj;
        return Glide.with(getContext())
                .load(obj)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mainThreadCallback(mLastBytesRead, mTotalBytes, true, e);
//                        ProgressManager.removeProgressListener(internalProgressListener);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mainThreadCallback(mLastBytesRead, mTotalBytes, true, null);
//                        ProgressManager.removeProgressListener(internalProgressListener);
                        return false;
                    }
                });
    }

    private final RequestOptions requestOptions(int placeholderResId) {
        return requestOptions(placeholderResId, placeholderResId);
    }

    private final RequestOptions requestOptions(Drawable placeholderResId) {
        return requestOptions(placeholderResId, placeholderResId);
    }

    private final RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }

    private final RequestOptions requestOptions(Drawable placeholderResId, Drawable errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }

    private final RequestOptions circleRequestOptions(int placeholderResId) {
        return circleRequestOptions(placeholderResId, placeholderResId);
    }

    private final RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId)
                .transform(new CircleTransformation());
    }

    /**
     * 加载带有缺省占位图的
     * @param url
     * @param placeholderResId
     */
    public final void loadImage(String url, int placeholderResId) {
        load(url, requestOptions(placeholderResId));
    }

    /**
     * 加载带有缺省占位图的
     * @param url
     * @param placeholderResId
     */
    public final void loadImage(String url, Drawable placeholderResId) {
        load(url, requestOptions(placeholderResId));
    }

    /**
     * 加载图片
     * @param url
     */
    public final void loadImage(String url) {
        load(url, new RequestOptions());
    }

    /**
     * 加载drawble图片
     * @param resId
     * @param placeholderResId
     */
    public final void loadLocalImage(@DrawableRes int resId, int placeholderResId) {
        load(resId, requestOptions(placeholderResId));
    }

    /**
     * 加载本地图片
     * @param localPath
     * @param placeholderResId
     */
    public final void loadLocalImage(String localPath, int placeholderResId) {
        load(FILE + localPath, requestOptions(placeholderResId));
    }

    /**
     * 圆形图片
     * @param url
     * @param placeholderResId
     */
    public final void loadCircleImage(String url, int placeholderResId) {
        load(url, circleRequestOptions(placeholderResId));
    }

    /**
     * 加载drawable图片、生成圆形图片
     * @param resId
     * @param placeholderResId
     */
    public final void loadLocalCircleImage(int resId, int placeholderResId) {
        load(resId, circleRequestOptions(placeholderResId));
    }

    /**
     * 加载本地图片、生成圆形图片
     * @param localPath
     * @param placeholderResId
     */
    public final void loadLocalCircleImage(String localPath, int placeholderResId) {
        load(FILE + localPath, circleRequestOptions(placeholderResId));
    }

    private void addProgressListener() {
        if (getImageUrl() == null) return;
        final String url = getImageUrl();
        if (!url.startsWith(HTTP)) return;

//        internalProgressListener = new OnProgressListener() {
//            @Override
//            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
//                if (totalBytes == 0) return;
//                if (!url.equals(imageUrl)) return;
//                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;
//
//                mLastBytesRead = bytesRead;
//                mTotalBytes = totalBytes;
//                mLastStatus = isDone;
//                mainThreadCallback(bytesRead, totalBytes, isDone, exception);
//
//                if (isDone) {
//                    ProgressManager.removeProgressListener(this);
//                }
//            }
//        };
//        ProgressManager.addProgressListener(internalProgressListener);
    }

    private void mainThreadCallback(final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
//                final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
//                if (onProgressListener != null) {
//                    onProgressListener.onProgress((String) mImageUrlObj, bytesRead, totalBytes, isDone, exception);
//                }
//
//                if (onGlideImageViewListener != null) {
//                    onGlideImageViewListener.onProgress(percent, isDone, exception);
//                }
            }
        });
    }

//    public void setOnGlideImageViewListener(String imageUrl, OnGlideImageViewListener onGlideImageViewListener) {
//        this.mImageUrlObj = imageUrl;
//        this.onGlideImageViewListener = onGlideImageViewListener;
//        addProgressListener();
//    }

//    public void setOnProgressListener(String imageUrl, OnProgressListener onProgressListener) {
//        this.mImageUrlObj = imageUrl;
//        this.onProgressListener = onProgressListener;
//        addProgressListener();
//    }
}
