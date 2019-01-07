package com.conan.gankmvvm.viewmodel;

import android.app.Application;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.utils.AppUtil;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class GankItemViewModel extends AndroidViewModel {

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
