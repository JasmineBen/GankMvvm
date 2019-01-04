package com.conan.gankmvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.conan.gankmvvm.GankApplication;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.data.repository.IRepository;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

public class GankListViewModel extends AndroidViewModel {

    private static final String TAG = "GankListViewModel";

    @Inject
    IRepository mRepository;

    public GankListViewModel(@NonNull Application application) {
        super(application);
        GankApplication app = (GankApplication)application;
        app.getAppComponent().inject(this);
    }

    public LiveData<GankList> fetchGankList(final GankApi.GankDataType type, final int pageIndex, final int pageSize){
        LogUtil.i(TAG, "fetchGankList type:" + type+",pageIndex:"+pageIndex+",pageSize:"+pageSize);
        return mRepository.getRemoteGankList(type.getDataType(), pageIndex, pageSize);
    }

    public  LiveData<List<GankEntity>> fetchCacheList(final GankApi.GankDataType type, final int pageIndex, final int pageSize){
        return mRepository.getLocalGankList(type.getDataType(), pageIndex, pageSize);
    }

}
