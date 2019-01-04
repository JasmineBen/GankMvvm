package com.conan.gankmvvm.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;

import java.util.List;

import io.reactivex.Observer;

/**
 * Description：资源层接口
 * Created by：JasmineBen
 * Time：2017/11/2
 */

public interface IRepository {
    LiveData<GankList> getRemoteGankList(String type, int pageIndex, int pageSize);

    LiveData<List<GankEntity>> getLocalGankList(String type, int pageIndex, int pageSize);

    void cacheGankList(GankList gankList);

}
