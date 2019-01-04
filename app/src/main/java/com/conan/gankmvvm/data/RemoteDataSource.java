package com.conan.gankmvvm.data;

import com.conan.gankmvvm.data.network.RequestManager;
import com.conan.gankmvvm.model.GankList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

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

    public Call<GankList> fetchGankList(String type, int pageIndex, int pageSize){
        return mRequestManager.getApi().getGankList(type,pageSize,pageIndex);
    }

}
