package com.conan.gankmvvm.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.conan.gankmvvm.R;
import com.conan.gankmvvm.utils.GlideApp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingUtil {

    @BindingAdapter({"welfareImageUrl"})
    public static void loadWelfareImage(ImageView imgView, String url) {
        if(!TextUtils.isEmpty(url)) {
            Log.i("zpy",url);
            int columnWidth = AppUtil.getColumnWidth(imgView.getContext(), 2, 16);
            url = AppUtil.buildRequestImageParam(imgView.getContext(), url, columnWidth);
            imgView.setLayoutParams(new FrameLayout.LayoutParams(columnWidth, columnWidth));
            GlideApp.with(imgView.getContext()).load(url).centerCrop()
                    .placeholder(R.color.c_fafafa).error(R.color.c_fafafa)
                    .into(imgView);
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadItemImage(ImageView imgView, String url) {
        if(!TextUtils.isEmpty(url)) {
            url = AppUtil.buildRequestImageParam(imgView.getContext(), url, 72);
            GlideApp.with(imgView.getContext())
                    .load(url).centerCrop()
                    .placeholder(R.color.c_fafafa).error(R.color.c_fafafa)
                    .into(imgView);
        }
    }

    @BindingConversion
    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


}
