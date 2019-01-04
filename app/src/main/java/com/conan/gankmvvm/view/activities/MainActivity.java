package com.conan.gankmvvm.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.conan.gankmvvm.R;
import com.conan.gankmvvm.databinding.ActivityMainBinding;
import com.conan.gankmvvm.model.MainTab;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.utils.GlideApp;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.view.adapter.MainTabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：主Activity
 * Created by：JasmineBen
 * Time：2017/10/31
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNecessaryPermissionGranted() {
        getActivityComponent().inject(this);
        initViews();
    }

    protected void initViews() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initToolbar();
        initNavigationView();
        initDrawerLayout();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        Toolbar toolbar = mBinding.mainToolbar.toolbar;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void initDrawerLayout(){
        mDrawerLayout = mBinding.drawerLeft;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mBinding.mainToolbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViewPager() {
        mViewPager = mBinding.viewpager;
        String[] tabs = getResources().getStringArray(R.array.study_tab);
        List<MainTab> mainTabs = new ArrayList<>(tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            MainTab tab = new MainTab(tabs[i], i, AppUtil.parseGankDataType(tabs[i]));
            mainTabs.add(tab);
        }
        mViewPager.setOffscreenPageLimit(mainTabs.size());
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(this, mainTabs);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout = mBinding.tabs;
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initNavigationView(){
        mNavigationView = mBinding.navigationView;
        mNavigationView.setNavigationItemSelectedListener(this);
        View headView = mNavigationView.getHeaderView(0);
        ImageView headImage = headView.findViewById(R.id.header);
        GlideApp.with(this).load(R.mipmap.navigation_header).transform(new CircleCrop()).into(headImage);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        LogUtil.i(TAG, "onNavigationItemSelected :" + item.getItemId());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.welfare:
                startActivity(new Intent(this,WelfareActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

}
