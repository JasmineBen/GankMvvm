package com.conan.gankmvvm.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.conan.gankmvvm.BuildConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;


/**
 * Description：Gank实体
 * Created by：JasmineBen
 * Time：2017/10/28
 */
@Entity
public class GankEntity{

    @PrimaryKey
    @SerializedName("_id")
    @NonNull
    private String id;
    
    @SerializedName("createdAt")
    private Date createdTime;

    @SerializedName("desc")
    private String desc;

    @SerializedName("type")
    private String type;

    @SerializedName("publishedAt")
    private Date publishedTime;

    @SerializedName("source")
    private String source;

    @SerializedName("url")
    private String url;

    @SerializedName("used")
    private boolean used;

    @SerializedName("who")
    private String publisher;

    @SerializedName("images")
    private List<String> images;

    public GankEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GankEntity)) {
            return false;
        }
        return this.getId().equals(((GankEntity) obj).getId());
    }

    @Override
    public String toString() {
        if(BuildConfig.DEBUG) {
            return "GankEntity{" +
                    "id='" + id + '\'' +
                    ", createdTime='" + createdTime + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedTime='" + publishedTime + '\'' +
                    ", source='" + source + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", publisher='" + publisher + '\'' +
                    ", images=" + images +
                    '}';
        }else{
            return super.toString();
        }
    }

    public boolean getUsed() {
        return this.used;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover(){
        if(images != null && images.size() > 0){
            return images.get(0);
        }
        return null;
    }

}
