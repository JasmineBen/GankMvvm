package com.conan.gankmvvm.data.database;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converts {

    @TypeConverter

    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String convertStringList(List<String> list){
        if(list != null && list.size() > 0){
            JSONArray jsonArray = new JSONArray();
            for(String entity : list){
                jsonArray.put(entity);
            }
            return jsonArray.toString();
        }
        return null;
    }

    @TypeConverter
    public static List<String> convertStringToList(String databaseValue) {
        if (!TextUtils.isEmpty(databaseValue)) {
            try {
                JSONArray jsonArray = new JSONArray(databaseValue);
                List<String> result = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(jsonArray.getString(i));
                }
                return result;
            } catch (Exception e) {

            }
        }
        return null;
    }
}
