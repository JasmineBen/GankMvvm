package com.conan.gankmvvm.view.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.conan.gankmvvm.GankApplication;
import com.conan.gankmvvm.R;
import com.conan.gankmvvm.di.component.ActivityComponent;
import com.conan.gankmvvm.di.component.ApplicationComponent;
import com.conan.gankmvvm.di.component.DaggerActivityComponent;
import com.conan.gankmvvm.di.module.ActivityModule;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.GankApplication;
import com.conan.gankmvvm.di.component.ActivityComponent;
import com.conan.gankmvvm.di.component.ApplicationComponent;
import com.conan.gankmvvm.di.module.ActivityModule;
import com.conan.gankmvvm.utils.LogUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Description：基类Activity
 * Created by：JasmineBen
 * Time：2017/10/30
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();


    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getAppComponent().inject(this);
        mActivityComponent = DaggerActivityComponent.builder().
                applicationComponent(getAppComponent()).
                activityModule(new ActivityModule(this)).build();
        requestNecessaryPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.i(TAG, "onOptionsItemSelected:" + (item.getItemId() == android.R.id.home));
        switch (item.getItemId()){
            case R.id.action_settings:
                LogUtil.i(TAG, "action_settings");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private ApplicationComponent getAppComponent() {
        return ((GankApplication) getApplication()).getAppComponent();
    }

    private void requestNecessaryPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {}

            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                if(!aBoolean){
                    finish();
                }else{
                    onNecessaryPermissionGranted();
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                finish();
            }

            @Override
            public void onComplete() {}
        };

        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
    }

    protected abstract void onNecessaryPermissionGranted();

    protected ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
}
