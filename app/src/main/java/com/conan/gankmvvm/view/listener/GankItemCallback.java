package com.conan.gankmvvm.view.listener;

import com.conan.gankmvvm.model.GankEntity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;


public class GankItemCallback extends DiffUtil.ItemCallback<GankEntity> {

    public GankItemCallback(){
        super();
    }
    @Override
    public boolean areItemsTheSame(@NonNull GankEntity oldItem, @NonNull GankEntity newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull GankEntity oldItem, @NonNull GankEntity newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
}
