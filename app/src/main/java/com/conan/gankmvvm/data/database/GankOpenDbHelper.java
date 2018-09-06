package com.conan.gankmvvm.data.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.conan.gankmvvm.data.database.dao.DaoMaster;

import javax.inject.Singleton;

/**
 * Description：GreedDao OpenHelper
 * Created by：JasmineBen
 * Time：2017/11/3
 */
@Singleton
public class GankOpenDbHelper extends DaoMaster.OpenHelper{

    public GankOpenDbHelper(Context context, String name) {
        super(context, name);
    }

    public GankOpenDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
