package com.conan.gankmvvm.view.fragments;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.databinding.GankListLayoutBinding;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.Constants;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.view.adapter.GankListAdapter;
import com.conan.gankmvvm.view.listener.OnItemClickListener;
import com.conan.gankmvvm.viewmodel.GankListViewModel;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.widget.GankRecyclerView;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.Constants;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.view.adapter.GankListAdapter;
import com.conan.gankmvvm.view.listener.OnItemClickListener;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;
import com.conan.gankmvvm.widget.GankRecyclerView;

import javax.inject.Inject;

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
        mViewModel.getLoadMoreTaskEvent().observe(this, new Observer<GankList>() {
            @Override
            public void onChanged(@Nullable GankList gankList) {
                fetchGankListSuccess(gankList,false);
            }
        });
        mViewModel.getRefreshTaskEvent().observe(this, new Observer<GankList>() {
            @Override
            public void onChanged(@Nullable GankList gankList) {
                fetchGankListSuccess(gankList,true);
            }
        });
        mBinding.setViewmodel(mViewModel);
        initViews();
        return mBinding.getRoot();
    }

    @Override
    public void onRefresh() {
        mViewModel.fetchGankList(mDataType, AppUtil.getPageIndex(mAdapter.getItemCount(),
                Constants.PAGE_SIZE), Constants.PAGE_SIZE);
    }

    @Override
    public void onLoadMore() {
        if(!mSwipeRefreshLayout.isRefreshing()) {
            mViewModel.fetchGankList(mDataType, AppUtil.getPageIndex(mAdapter.getItemCount(),
                    Constants.PAGE_SIZE), Constants.PAGE_SIZE);
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
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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

    private void fetchGankListSuccess(GankList gankList, boolean refresh) {
        if(gankList == null){
            fetchGankListComplete();
            return;
        }
        mAdapter.setData(gankList,refresh);
        fetchGankListComplete();
    }


    private void fetchGankListComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }
}
