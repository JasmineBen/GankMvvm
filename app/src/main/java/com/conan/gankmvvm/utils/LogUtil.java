package com.conan.gankmvvm.utils;

import android.util.Log;

import com.conan.gankmvvm.BuildConfig;

/**
 * Description：Log帮助类
 * Created by：JasmineBen
 * Time：2017/10/29
 */
public class LogUtil {

    private static final String GLOBAL_TAG = "gankmvvm";

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(GLOBAL_TAG, "[" + tag + "] " + msg);
        }
    }
}
