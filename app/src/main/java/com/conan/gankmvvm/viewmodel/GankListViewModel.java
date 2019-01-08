package com.conan.gankmvvm.viewmodel;

import android.app.Application;
import android.util.Log;

import com.conan.gankmvvm.GankApplication;
import com.conan.gankmvvm.data.database.GankDatabase;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.data.repository.IRepository;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.Constants;
import com.conan.gankmvvm.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

public class GankListViewModel extends AndroidViewModel {

    private GankApi.GankDataType mDataType;
    private int mPage = Constants.FIRST_PAGE;
    private LiveData<PagedList<GankEntity>> mLiveData;

    @Inject
    IRepository mRepository;

    public GankListViewModel(@NonNull Application application) {
        super(application);
        GankApplication app = (GankApplication)application;
        app.getAppComponent().inject(this);
    }

    public void setDataType(GankApi.GankDataType type){
        mDataType = type;
    }

    public void initPageList() {
        mLiveData = new LivePagedListBuilder(getPositionalDataSource(),
                new PagedList.Config.Builder()
                        .setPageSize(Constants.PAGE_SIZE)
                        //距离本页数据几个时候开始加载下一页数据(例如现在加载10个数据,设置prefetchDistance为2,则滑到第八个数据时候开始加载下一页数据).
                        .setPrefetchDistance(Constants.PAGE_SIZE / 2)
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(Constants.PAGE_SIZE).build()).build();
    }

    private DataSource.Factory getPositionalDataSource() {
        if(AppUtil.isNetworkConnected(getApplication())) {
            return new PageDataSourceFactory(new PositionalDataSource<GankEntity>() {
                @Override
                public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<GankEntity> callback) {
                    // 计算显示到第几条数据
                    final int position = computeInitialLoadPosition(params, Constants.PAGE_SIZE);
                    final int loadSize = computeInitialLoadSize(params, position, Constants.PAGE_SIZE);
                    Log.i("zpy", "loadInitial mPage:" + mPage + ";position:" + position + ";loadSize:" + loadSize);
                    mRepository.getRemoteGankList(mDataType.getDataType(), mPage, Constants.PAGE_SIZE, results -> {
                        callback.onResult(results, position, Constants.PAGE_SIZE);
                        if (results != null && results.size() > 0) {
                            Log.i("zpy", "loadInitial onResult:" + results.size());
                            mPage++;
                        }
                    });
                }

                @Override
                public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<GankEntity> callback) {
                    Log.i("zpy", "loadRange mPage:" + mPage + ";pageSize:" + params.loadSize);
                    mRepository.getRemoteGankList(mDataType.getDataType(), mPage, params.loadSize, results -> {
                        callback.onResult(results);
                        if (results != null && results.size() > 0) {
                            Log.i("zpy", "loadRange onResult:" + results.size());
                            mPage++;
                        }
                    });
                }
            });
        }else{
            return GankDatabase.getInstance(getApplication()).gankDao().queryGankList(mDataType.getDataType());
        }
    }

    public LiveData<PagedList<GankEntity>> getLiveData() {
        return mLiveData;
    }

    public void forceRefresh(){
        mPage = 1;
       initPageList();
    }

    public class PageDataSourceFactory<GankEntity> extends DataSource.Factory{
        public PositionalDataSource<GankEntity> mPositionDataSource;

        public PageDataSourceFactory(PositionalDataSource<GankEntity> positionalDataSource){
            this.mPositionDataSource = positionalDataSource;
        }

        @Override
        public DataSource create() {
            return mPositionDataSource;
        }
    }

    public LiveData<List<GankEntity>> fetchGankList(final GankApi.GankDataType type, final int pageIndex, final int pageSize){
        return mRepository.getRemoteGankList(type.getDataType(), pageIndex, pageSize);
    }

    public interface ILoadedlistener{
        void onLoadedResult(List<GankEntity> results);
    }

}
