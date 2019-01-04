package com.conan.gankmvvm.di.component;

import android.content.Context;

import com.conan.gankmvvm.data.repository.IRepository;
import com.conan.gankmvvm.di.module.ApplicationModule;
import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.viewmodel.GankListViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Description：Dragger Application Component
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity base);
    void inject(GankListViewModel viewModel);
    //暴露出来给dependencies它的Component使用
    Context applicationContext();
    IRepository gankRepository();
}
