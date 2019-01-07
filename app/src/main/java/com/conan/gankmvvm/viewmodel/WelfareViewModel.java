package com.conan.gankmvvm.viewmodel;

import android.app.Application;

import com.conan.gankmvvm.model.GankEntity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class WelfareViewModel extends AndroidViewModel {

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
