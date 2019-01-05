package com.conan.gankmvvm.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.conan.gankmvvm.data.database.GankDatabase;
import com.conan.gankmvvm.model.GankEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
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
            List<GankEntity> toBeInsert = new ArrayList<>();
            List<GankEntity> toBeUpdate = new ArrayList<>();
            if(gankDatas != null){
                for(GankEntity entity : gankDatas){
                    if(GankDatabase.getInstance(applicationContext).gankDao().checkExist(entity.getId()) != null){
                        toBeUpdate.add(entity);
                    }else{
                        toBeInsert.add(entity);
                    }
                }
                if(toBeInsert.size() > 0){
                    GankDatabase.getInstance(applicationContext).gankDao().saveGankList(toBeInsert);
                }
                if(toBeUpdate.size() > 0){
                    GankDatabase.getInstance(applicationContext).gankDao().updateGankList(toBeUpdate);
                }
            }
        });
    }

    public LiveData<List<GankEntity>> queryGankList(final String type, final int pageIndex, final int pageSize) {
        return GankDatabase.getInstance(applicationContext).gankDao().queryGankList(type,pageIndex,pageSize);
    }
}
