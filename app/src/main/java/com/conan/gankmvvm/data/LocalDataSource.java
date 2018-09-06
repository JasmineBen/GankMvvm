package com.conan.gankmvvm.data;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.data.database.dao.DaoMaster;
import com.conan.gankmvvm.data.database.dao.DaoSession;
import com.conan.gankmvvm.data.database.dao.GankEntityDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Description：本地数据访问接口
 * Created by：JasmineBen
 * Time：2017/11/4
 */
@Singleton
public class LocalDataSource {

    private DaoSession mSession;

    @Inject
    public LocalDataSource(DaoMaster.OpenHelper openDbHelper) {
        mSession = new DaoMaster(openDbHelper.getReadableDb()).newSession();
    }

    public Observable<Boolean> cacheGankList(final List<GankEntity> gankDatas) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mSession.getGankEntityDao().insertOrReplaceInTx(gankDatas);
                return true;
            }
        });
    }

    public Observable<GankList> queryGankList(final String type, final int pageIndex, final int pageSize) {
        return Observable.fromCallable(new Callable<GankList>() {
            @Override
            public GankList call() throws Exception {
                Query<GankEntity> query = mSession.getGankEntityDao().queryBuilder()
                        .where(GankEntityDao.Properties.Type.eq(type))
                        .offset((pageIndex - 1) * pageSize)
                        .limit(pageSize)
                        .build();
                List<GankEntity> gankDatas = query.list();
                GankList list = new GankList(type);
                list.setGankDatas(gankDatas);
                list.setType(type);
                return list;
            }
        });

    }
}
