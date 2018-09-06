package com.conan.gankmvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.utils.AppUtil;
import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.utils.AppUtil;

public class GankItemViewModel extends AndroidViewModel{

    public final ObservableField<GankEntity> entity = new ObservableField<>();

    public GankItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void setData(GankEntity entity){
        this.entity.set(entity);
    }

    public String getCover(){
        return entity.get().getCover();
    }

    public String getDesc(){
        return entity.get().getDesc();
    }

    public String getPublisher(){
        return entity.get().getPublisher();
    }

    public String getPublishTime(){
        return AppUtil.parseSimpleDate(entity.get().getCreatedTime());
    }
}
