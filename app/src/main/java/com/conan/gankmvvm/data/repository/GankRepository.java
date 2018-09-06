package com.conan.gankmvvm.data.repository;

import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.data.LocalDataSource;
import com.conan.gankmvvm.data.RemoteDataSource;
import com.conan.gankmvvm.data.LocalDataSource;
import com.conan.gankmvvm.data.RemoteDataSource;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void getRemoteGankList(Observer<GankList> observer, String type, int pageIndex, int pageSize){
        Observable<GankList> observable = mRemoteDataSource.fetchGankList(type,pageIndex,pageSize);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    public void getLocalGankList(Observer<GankList> observer, String type, int pageIndex, int pageSize){
        Observable<GankList> observable =  mLocalDataSource.queryGankList(type,pageIndex,pageSize);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    public void cacheGankList(Observer<Boolean> observer,GankList gankList){
        Observable observable = mLocalDataSource.cacheGankList(gankList.getGankDatas());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

}
