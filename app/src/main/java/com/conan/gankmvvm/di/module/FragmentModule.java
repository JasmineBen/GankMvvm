package com.conan.gankmvvm.di.module;

import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.di.scope.PerFragment;
import com.conan.gankmvvm.view.fragments.BaseFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Description：Dagger Fragment Module
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Module
public class FragmentModule {

    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    public BaseActivity provideActivity() {
        return (BaseActivity)mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public BaseFragment provideFragment() {
        return mFragment;
    }

}
