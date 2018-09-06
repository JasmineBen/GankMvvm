package com.conan.gankmvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.conan.gankmvvm.model.GankEntity;

public class WelfareViewModel extends AndroidViewModel{

    public final ObservableField<GankEntity> entity = new ObservableField<>();

    public WelfareViewModel(@NonNull Application application) {
        super(application);
    }

    public void setData(GankEntity entity){
        this.entity.set(entity);
    }

    public String getCover(){
        return entity.get().getUrl();
    }

}
