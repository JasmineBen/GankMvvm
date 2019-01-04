package com.conan.gankmvvm.data.database.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.conan.gankmvvm.model.GankEntity;
import com.conan.gankmvvm.model.GankList;

import java.util.List;

/**
 * DAO for table "GANK_ENTITY".
*/
@Dao
public interface GankDao {

    @Insert
    void cacheGankList(final List<GankEntity> gankDatas) ;

    @Query("SELECT * FROM gankentity WHERE type = :type limit :pageSize offset :pageSize*(:pageIndex-1)")
    LiveData<List<GankEntity>> queryGankList(final String type, final int pageIndex, final int pageSize);

    
}