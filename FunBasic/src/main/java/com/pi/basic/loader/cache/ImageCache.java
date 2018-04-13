package com.pi.basic.loader.cache;


import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @描述：     @图片缓存配置
 * @作者：     @蒋诗朋
 * @创建时间： @2017-04-25
 */
@GlideModule
public final class ImageCache extends AppGlideModule {
    private final static int DISK_CACHE_SIZE = 1024 * 1024 * 100; // 100 MB
    //配置磁盘缓存
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));
    }
}
