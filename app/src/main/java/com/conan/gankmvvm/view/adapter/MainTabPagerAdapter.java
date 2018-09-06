package com.conan.gankmvvm.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.model.MainTab;
import com.conan.gankmvvm.view.fragments.MainTabFragment;
import com.conan.gankmvvm.model.MainTab;
import com.conan.gankmvvm.view.activities.BaseActivity;

import java.util.List;

/**
 * Description：主界面PagerAdapter
 * Created by：JasmineBen
 * Time：2017/10/31
 */
public class MainTabPagerAdapter extends FragmentPagerAdapter {

    private final List<MainTab> mainTabs;

    public MainTabPagerAdapter(BaseActivity activity, List<MainTab> tabs) {
        super(activity.getSupportFragmentManager());
        this.mainTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return MainTabFragment.newInstance(mainTabs.get(position).getDataType());
    }

    @Override
    public int getCount() {
        return mainTabs == null ? 0 : mainTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mainTabs.get(position).getTitle();
    }
}
