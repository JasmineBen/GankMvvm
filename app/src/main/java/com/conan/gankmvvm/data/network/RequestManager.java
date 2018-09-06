package com.conan.gankmvvm.data.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description：Network Manager
 * Created by：JasmineBen
 * Time：2017/10/30
 */
@Singleton
public class RequestManager {


    private static final long CONNECT_TIMEOUT = 5;
    private static final long READ_TIMEOUT = 10;
    private GankApi mApi;

    @Inject
    public RequestManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder().setDateFormat(GankApi.DATE_FORMAT_CONVERT).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        mApi = retrofit.create(GankApi.class);
    }

    public GankApi getApi() {
        return mApi;
    }
}
