package com.conan.gankmvvm.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.databinding.GankListLayoutBinding;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.Constants;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.view.adapter.GankListAdapter;
import com.conan.gankmvvm.view.listener.OnItemClickListener;
import com.conan.gankmvvm.viewmodel.GankListViewModel;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.widget.GankRecyclerView;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Description：TabFragment
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class MainTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,GankRecyclerView.OnLoadMoreListener {

    private static final String TAG = MainTabFragment.class.getSimpleName();

    private GankApi.GankDataType mDataType;

    SwipeRefreshLayout mSwipeRefreshLayout;

    GankRecyclerView mRecyclerView;

    @Inject
    GankListAdapter mAdapter;

    private GankListLayoutBinding mBinding;
    private GankListViewModel mViewModel;

    public static MainTabFragment newInstance(GankApi.GankDataType dataType) {
        MainTabFragment fragment = new MainTabFragment();
        Bundle data = new Bundle();
        data.putString(Constants.GANK_DATA_TYPE, dataType.getDataType());
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            mDataType = AppUtil.parseGankDataType(data.getString(Constants.GANK_DATA_TYPE));
            LogUtil.i(TAG, "" + mDataType);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = GankListLayoutBinding.inflate(inflater,container,false);
        mViewModel = obtainViewModel();
        mBinding.setViewmodel(mViewModel);
        initViews();
        return mBinding.getRoot();
    }

    @Override
    public void onRefresh() {
        LiveData<GankList> liveData = mViewModel.fetchGankList(mDataType, 1, Constants.PAGE_SIZE);
        liveData.observe(this,gankList->{
            Log.i("zpy","onRefresh result:"+gankList);
            if(gankList == null && mAdapter.getItemCount() == 0){
                LiveData<List<GankEntity>> cachedList = mViewModel.fetchCacheList(mDataType,1,Constants.PAGE_SIZE);
                cachedList.observe(MainTabFragment.this, gankEntities ->  {
                    GankList data = new GankList(mDataType.getDataType());
                    data.addItems(gankEntities);
                    fetchGankListSuccess(data, 1);
                });

            }else {
                fetchGankListSuccess(gankList, 1);
            }
        });
    }

    @Override
    public void onLoadMore() {
        if(!mSwipeRefreshLayout.isRefreshing()) {
            final int pageIndex = AppUtil.getPageIndex(mAdapter.getItemCount(),
                    Constants.PAGE_SIZE);
            LiveData<GankList> liveData = mViewModel.fetchGankList(mDataType, pageIndex, Constants.PAGE_SIZE);
            liveData.observe(this,gankList->{
                fetchGankListSuccess(gankList,pageIndex);
            });
        }
    }

    @Override
    protected void injectDagger() {
        getFragmentComponent().inject(this);
    }

    private void initViews() {
        initSwipeView();
        intRecyclerView();
        initData();
    }

    private void intRecyclerView() {
        mRecyclerView = mBinding.recyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(GankEntity gank) {
                if(!TextUtils.isEmpty(gank.getUrl())){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(gank.getUrl());
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
    }

    private void initSwipeView() {
        mSwipeRefreshLayout = mBinding.swipeLayout;
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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

    private GankListViewModel obtainViewModel(){
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        GankListViewModel viewModel =  factory.create(GankListViewModel.class);
        return viewModel;
    }

    private void fetchGankListSuccess(GankList gankList, int pageIndex) {
        Log.i("zpy","fetchGankListSuccess:"+gankList);
        if(gankList == null){
            fetchGankListComplete();
            return;
        }
        mAdapter.setData(gankList,pageIndex == 1);
        fetchGankListComplete();
    }


    private void fetchGankListComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }
}
