package com.conan.gankmvvm.di.component;

import com.conan.gankmvvm.di.module.ActivityModule;
import com.conan.gankmvvm.di.scope.PerActivity;
import com.conan.gankmvvm.view.activities.MainActivity;
import com.conan.gankmvvm.view.activities.WelfareActivity;

import dagger.Component;

/**
 * Description：Dragger Activity Component
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(WelfareActivity welfareActivity);
}
