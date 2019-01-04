package com.conan.gankmvvm.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.conan.gankmvvm.data.database.GankDatabase;
import com.conan.gankmvvm.model.GankEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Description：本地数据访问接口
 * Created by：JasmineBen
 * Time：2017/11/4
 */
@Singleton
public class LocalDataSource {

    private Context applicationContext;
    private Executor executor = Executors.newCachedThreadPool();

    @Inject
    public LocalDataSource(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public void cacheGankList(final List<GankEntity> gankDatas) {
        executor.execute(()->{
            GankDatabase.getInstance(applicationContext).gankDao().cacheGankList(gankDatas);
        });
    }

    public LiveData<List<GankEntity>> queryGankList(final String type, final int pageIndex, final int pageSize) {
        return GankDatabase.getInstance(applicationContext).gankDao().queryGankList(type,pageIndex,pageSize);
    }
}
