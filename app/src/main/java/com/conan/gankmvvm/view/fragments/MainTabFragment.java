package com.conan.gankmvvm.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankmvvm.R;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.databinding.GankListLayoutBinding;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.Constants;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.view.adapter.GankListAdapter;
import com.conan.gankmvvm.viewmodel.GankListViewModel;
import com.conan.gankmvvm.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Description：TabFragment
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class MainTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainTabFragment.class.getSimpleName();

    private GankApi.GankDataType mDataType;

    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView mRecyclerView;

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
        LogUtil.i(TAG, "onCreateView " + mDataType);
        mViewModel.setDataType(mDataType);
        mViewModel.initPageList();
        mBinding.setViewmodel(mViewModel);
        initViews();
        return mBinding.getRoot();
    }

    @Override
    public void onRefresh() {
        mViewModel.forceRefresh();
        observeLiveData();
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
        mAdapter.setOnItemClickListener(gank -> {
            if(!TextUtils.isEmpty(gank.getUrl())){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(gank.getUrl());
                intent.setData(uri);
                startActivity(intent);
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
        mSwipeRefreshLayout.setRefreshing(true);
        observeLiveData();
    }

    private void observeLiveData() {
        mViewModel.getLiveData().observe(this, gankEntities -> {
            mAdapter.submitList(gankEntities);
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private GankListViewModel obtainViewModel(){
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        GankListViewModel viewModel =  factory.create(GankListViewModel.class);
        return viewModel;
    }

}
