package com.conan.gankmvvm.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.conan.gankmvvm.GankApplication;
import com.conan.gankmvvm.di.component.DaggerFragmentComponent;
import com.conan.gankmvvm.di.component.FragmentComponent;
import com.conan.gankmvvm.di.module.FragmentModule;
import com.conan.gankmvvm.GankApplication;
import com.conan.gankmvvm.di.component.FragmentComponent;
import com.conan.gankmvvm.di.module.FragmentModule;

/**
 * Description：基类Fragment
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public abstract class BaseFragment extends Fragment {


    private FragmentComponent mFragmentComponent;


    protected abstract void injectDagger();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((GankApplication) getActivity().getApplication()).getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        injectDagger();
    }


    protected FragmentComponent getFragmentComponent(){
        return mFragmentComponent;
    }
}
