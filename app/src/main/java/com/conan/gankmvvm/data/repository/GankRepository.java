package com.conan.gankmvvm.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.conan.gankmvvm.data.LocalDataSource;
import com.conan.gankmvvm.data.RemoteDataSource;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description：Gank数据资源层
 * Created by：JasmineBen
 * Time：2017/11/2
 */
@Singleton
public class GankRepository implements IRepository{

    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    @Inject
    public GankRepository(LocalDataSource localDataSource,RemoteDataSource remoteDataSource){
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    @Override
    public LiveData<GankList> getRemoteGankList(String type, int pageIndex, int pageSize){
        MutableLiveData<GankList> liveData = new MutableLiveData<>();
        Call<GankList> call = mRemoteDataSource.fetchGankList(type,pageIndex,pageSize);
        call.enqueue(new Callback<GankList>() {
            @Override
            public void onResponse(Call<GankList> call, Response<GankList> response) {
                Log.i("zpy","getRemoteGankList");
                if(pageIndex == 1 && response.body() != null){
                    mLocalDataSource.cacheGankList(response.body().getGankDatas());
                }
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GankList> call, Throwable t) {
                t.printStackTrace();
                Log.i("zpy","getRemoteGankList error");
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    @Override
    public LiveData<List<GankEntity>> getLocalGankList(String type, int pageIndex, int pageSize){
        LiveData<List<GankEntity>> liveData =  mLocalDataSource.queryGankList(type,pageIndex,pageSize);
        return liveData;
    }

    @Override
    public void cacheGankList(GankList gankList){
        mLocalDataSource.cacheGankList(gankList.getGankDatas());
    }

}
