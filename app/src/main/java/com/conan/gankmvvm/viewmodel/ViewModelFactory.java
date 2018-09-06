package com.conan.gankmvvm.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.VisibleForTesting;

import com.conan.gankmvvm.data.repository.GankRepository;

import javax.inject.Inject;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GankItemViewModel.class)) {
            //noinspection unchecked
            return (T) new GankItemViewModel(mApplication);
        }
        if (modelClass.isAssignableFrom(WelfareViewModel.class)) {
            //noinspection unchecked
            return (T) new WelfareViewModel(mApplication);
        }
        if (modelClass.isAssignableFrom(GankListViewModel.class)) {
            //noinspection unchecked
            return (T) new GankListViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
