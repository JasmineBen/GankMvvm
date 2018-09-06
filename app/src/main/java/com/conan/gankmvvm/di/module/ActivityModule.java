package com.conan.gankmvvm.di.module;

import com.conan.gankmvvm.view.activities.BaseActivity;
import com.conan.gankmvvm.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Description：Dragger Activity Module
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Module
public class ActivityModule {

    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity activity){
        this.baseActivity = activity;
    }

    @Provides
    @PerActivity
    BaseActivity provideActivity(){
        return baseActivity;
    }
}
