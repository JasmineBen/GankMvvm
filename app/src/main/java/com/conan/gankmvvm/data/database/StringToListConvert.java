package com.conan.gankmvvm.data.database;

import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：数据库String转化为List
 * Created by：JasmineBen
 * Time：2017/11/3
 */
public class StringToListConvert implements PropertyConverter<List<String>,String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if(!TextUtils.isEmpty(databaseValue)){
            try {
                JSONArray jsonArray = new JSONArray(databaseValue);
                List<String> result = new ArrayList<>(jsonArray.length());
                for(int i = 0;i<jsonArray.length();i++){
                    result.add(jsonArray.getString(i));
                }
                return result;
            }catch (Exception e) {

            }

        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if(entityProperty != null && entityProperty.size() > 0){
            JSONArray jsonArray = new JSONArray();
            for(String entity : entityProperty){
                jsonArray.put(entity);
            }
            return jsonArray.toString();
        }
        return null;
    }
}
