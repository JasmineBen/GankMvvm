package com.conan.gankmvvm.model;

import com.conan.gankmvvm.BuildConfig;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.utils.LogUtil;
import com.conan.gankmvvm.data.network.GankApi;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Description：Gank数据列表包装类
 * Created by：JasmineBen
 * Time：2017/11/1
 */
public class GankList extends GankException {

    @SerializedName("results")
    private List<GankEntity> gankDatas;
    private String type;

    public GankList(String type){
        this.type = type;
        this.gankDatas = new ArrayList<>();
    }

    public List<GankEntity> getGankDatas() {
        return gankDatas;
    }

    public void setGankDatas(List<GankEntity> gankDatas) {
        this.gankDatas = gankDatas;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GankApi.GankDataType getGankDataType(){
        LogUtil.i("GankList","type:"+type);
        for(GankApi.GankDataType dataType : GankApi.GankDataType.values()){
            if(dataType.getDataType().equals(type)){
                return dataType;
            }
        }
        return null;
    }

    public int size(){
        return gankDatas == null ? 0 :gankDatas.size();
    }

    public void clear(){
        if(gankDatas != null){
            gankDatas.clear();
        }
    }

    public GankEntity getItem(int position){
        if(gankDatas == null || position >= gankDatas.size()){
            return null;
        }
        return gankDatas.get(position);
    }

    @Override
    public String toString() {
        if(BuildConfig.DEBUG) {
            return "GankList{" +
                    "gankDatas=" + gankDatas +
                    ", type='" + type + '\'' +
                    '}';
        }else{
            return super.toString();
        }
    }

    public void addItems(List<GankEntity> list){
        if(list != null){
            gankDatas.addAll(list);
        }
    }
}
