package com.conan.gankmvvm.data;

import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.data.network.RequestManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Description：网络数据访问接口
 * Created by：JasmineBen
 * Time：2017/11/4
 */
@Singleton
public class RemoteDataSource {

    RequestManager mRequestManager;

    @Inject
    public RemoteDataSource(RequestManager manager){
        this.mRequestManager = manager;
    }

    public Observable<GankList> fetchGankList(String type, int pageIndex, int pageSize){
        return mRequestManager.getApi().getGankList(type,pageSize,pageIndex);
    }
}
