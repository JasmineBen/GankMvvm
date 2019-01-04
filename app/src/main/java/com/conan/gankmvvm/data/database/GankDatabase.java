package com.conan.gankmvvm.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.conan.gankmvvm.data.database.dao.GankDao;
import com.conan.gankmvvm.model.GankEntity;

@Database(entities = {GankEntity.class},version =1,exportSchema = false)
@TypeConverters({Converts.class})
public abstract class GankDatabase extends RoomDatabase {

    private static final String DB_NAME = "GanDatabase.db";
    private static volatile GankDatabase instance;

    public abstract GankDao gankDao();

    public static synchronized  GankDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance ;
    }
    private static GankDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                GankDatabase.class,
                DB_NAME).build();
    }

}
