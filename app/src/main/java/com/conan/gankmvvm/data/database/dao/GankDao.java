package com.conan.gankmvvm.data.database.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

import com.conan.gankmvvm.model.GankEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.TiledDataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * DAO for table "GANK_ENTITY".
*/
@Dao
public interface GankDao {

    @Insert
    void saveGankList(final List<GankEntity> datas) ;

    @Query("SELECT * FROM gankentity WHERE id=:id")
    GankEntity checkExist(String id);

    @Update
    void updateGankList(final List<GankEntity> datas);

    @Query("SELECT * FROM gankentity WHERE type = :type")
    DataSource.Factory<Integer,GankEntity> queryGankList(final String type);
    
}
