package com.conan.gankmvvm.data.repository;

import android.util.Log;

import com.conan.gankmvvm.data.LocalDataSource;
import com.conan.gankmvvm.data.RemoteDataSource;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.viewmodel.GankListViewModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    public void getRemoteGankList(String type, int pageIndex, int pageSize,GankListViewModel.ILoadedlistener listener){
        Call<GankList> call = mRemoteDataSource.fetchGankList(type,pageIndex,pageSize);
        call.enqueue(new Callback<GankList>() {
            @Override
            public void onResponse(Call<GankList> call, Response<GankList> response) {
                Log.i("zpy","getRemoteGankList pageIndex:"+pageIndex+";pageSize:"+pageSize);
                if(response.body() != null){
                    listener.onLoadedResult(response.body().getGankDatas());
                    mLocalDataSource.cacheGankList(response.body().getGankDatas());
                }
            }

            @Override
            public void onFailure(Call<GankList> call, Throwable t) {
                t.printStackTrace();
                Log.i("zpy","getRemoteGankList error");
                listener.onLoadedResult(null);
            }
        });
    }

    @Override
    public LiveData<List<GankEntity>> getRemoteGankList(String type, int pageIndex, int pageSize){
        MutableLiveData<List<GankEntity>> liveData = new MutableLiveData<>();
        Call<GankList> call = mRemoteDataSource.fetchGankList(type,pageIndex,pageSize);
        call.enqueue(new Callback<GankList>() {
            @Override
            public void onResponse(Call<GankList> call, Response<GankList> response) {
                if( response.body() != null){
                    liveData.setValue(response.body().getGankDatas());
                    mLocalDataSource.cacheGankList(response.body().getGankDatas());
                }
            }

            @Override
            public void onFailure(Call<GankList> call, Throwable t) {
                t.printStackTrace();
                liveData.setValue(null);
            }
        });
        return liveData;
    }


}
