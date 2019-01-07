package com.conan.gankmvvm.data.repository;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;

import java.util.List;

import androidx.lifecycle.LiveData;

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
