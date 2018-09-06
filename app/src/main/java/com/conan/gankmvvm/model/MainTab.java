package com.conan.gankmvvm.model;

import com.conan.gankmvvm.BuildConfig;
import com.conan.gankmvvm.data.network.GankApi;
import com.conan.gankmvvm.data.network.GankApi;

/**
 * Description：主界面Tab
 * Created by：JasmineBen
 * Time：2017/11/1
 */

public class MainTab {
    private String title;
    private int position;
    private GankApi.GankDataType dataType;

    public MainTab(String title, int position, GankApi.GankDataType dataType) {
        this.title = title;
        this.position = position;
        this.dataType = dataType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public GankApi.GankDataType getDataType() {
        return dataType;
    }

    public void setDataType(GankApi.GankDataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        if(BuildConfig.DEBUG) {
            return "MainTab{" +
                    "title='" + title + '\'' +
                    ", position=" + position +
                    ", dataType=" + dataType +
                    '}';
        }else{
            return super.toString();
        }
    }
}
