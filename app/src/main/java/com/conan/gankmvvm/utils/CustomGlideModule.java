package com.conan.gankmvvm.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import androidx.annotation.NonNull;

@GlideModule
public class CustomGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        int memoryCacheSizeBytes = 1024 * 1024 * 50; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        int diskCacheSizeBytes = 1024 *1024 *200;
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }
}
