package com.conan.gankmvvm.data.repository;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.viewmodel.GankListViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Description：资源层接口
 * Created by：JasmineBen
 * Time：2017/11/2
 */

public interface IRepository {
    void getRemoteGankList(String type, int pageIndex, int pageSize, GankListViewModel.ILoadedlistener listener);
}
