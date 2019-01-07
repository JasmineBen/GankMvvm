package com.conan.gankmvvm.view.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.databinding.WelfareLayoutBinding;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.Constants;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.view.adapter.WelfareAdapter;
import com.conan.gankmvvm.viewmodel.GankListViewModel;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.widget.GankRecyclerView;
import com.conan.gankmvvm.widget.WelfareItemDecoration;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Description：福利Activity
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class WelfareActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,GankRecyclerView.OnLoadMoreListener {

    private static final String TAG = WelfareActivity.class.getSimpleName();

    @Inject
    WelfareAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    GankRecyclerView mRecyclerView;
    WelfareLayoutBinding mBinding;
    GankListViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onNecessaryPermissionGranted() {
        getActivityComponent().inject(this);
        initViews();
    }

    private void initViews(){
        mBinding = DataBindingUtil.setContentView(this,R.layout.welfare_layout);
        mViewModel = obtainViewModel();
        mBinding.setViewmodel(mViewModel);
        customToolbar();
        initSwipeView();
        intRecyclerView();
        initData();
    }

    private void customToolbar(){
        Toolbar toolbar = mBinding.welfareToolbar.toolbar;
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setTitle(R.string.welfare);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"setNavigationOnClickListener");
                finish();
            }
        });
    }

    private void initSwipeView(){
        mSwipeRefreshLayout = mBinding.swipeLayout;
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void intRecyclerView() {
        mRecyclerView = mBinding.recyclerview;

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.addItemDecoration(new WelfareItemDecoration(16,2));
    }

    private void initData(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        LiveData<GankList> liveData = mViewModel.fetchGankList(GankApi.GankDataType.DATA_TYPE_WELFARE,1, Constants.PAGE_SIZE);
        liveData.observe(this,gankList ->  {
            fetchWelfareListSuccess(gankList,true);
        });
    }

    @Override
    public void onLoadMore() {
        if(!mSwipeRefreshLayout.isRefreshing()){
            int pageIndex = AppUtil.getPageIndex(mAdapter.getItemCount(), Constants.PAGE_SIZE);
            Log.i(TAG,"onLoadMore pageIndex:"+pageIndex);
            LiveData<GankList> liveData = mViewModel.fetchGankList(GankApi.GankDataType.DATA_TYPE_WELFARE,pageIndex,Constants.PAGE_SIZE);
            liveData.observe(this, gankList -> {
                fetchWelfareListSuccess(gankList,false);
            });
        }
    }

    public void fetchWelfareListSuccess(GankList gankList, boolean refresh) {
        if(gankList == null){
            fetchGankistComplete();
            return;
        }
        mAdapter.setData(gankList,refresh);
        Log.i("zpy","fetchWelfareListSuccess:"+mAdapter.getItemCount());
        fetchGankistComplete();
    }

    private void fetchGankistComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }

    @Override
    protected void onDestroy() {
        LogUtil.i(TAG,"onDestroy");
        super.onDestroy();
    }

    private GankListViewModel obtainViewModel(){
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        GankListViewModel viewModel =  factory.create(GankListViewModel.class);
        return viewModel;
    }
}
