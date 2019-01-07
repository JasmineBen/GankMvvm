package com.conan.gankmvvm.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.conan.gankmvvm.data.network.GankApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.content.ContextCompat;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Description：Util类
 * Created by：JasmineBen
 * Time：2017/10/29
 */
public class AppUtil {

    public static int getPageIndex(int currentCount, int pageSize) {
        return currentCount / pageSize + 1;
    }

    public static String parseSimpleDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static GankApi.GankDataType parseGankDataType(String type) {
        for (GankApi.GankDataType dataType : GankApi.GankDataType.values()) {
            if (dataType.getDataType().equals(type)) {
                return dataType;
            }
        }
        return null;
    }

    public static int getScreenWith(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dpToPx(Context context, float dp) {
        return (int) ((context.getResources().getDisplayMetrics().density * dp) + 0.5);
    }

    public static String buildRequestImageParam(Context context, String url, int dpSize) {
        return url + "?imageView2/0/w/" + dpToPx(context, dpSize);
    }

    public static String getImageCacheDirectory(Context context, String dir) {
        String cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && hasExternalStoragePermission(context)) {
            cacheDir = Environment.getExternalStorageDirectory() + "/" + dir;
        }
        return cacheDir;
    }

    public static boolean hasExternalStoragePermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void cancelRxDisposables(CompositeDisposable disposable) {
        if (disposable != null) {
            disposable.clear();
        }
    }

    public static int getColumnWidth(Context context, int columns, int itemDecoration) {
        return (getScreenWith(context) - (columns + 1) * itemDecoration) / columns;
    }

}
