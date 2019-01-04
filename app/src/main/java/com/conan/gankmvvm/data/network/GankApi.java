package com.conan.gankmvvm.data.network;

import com.conan.gankmvvm.model.GankList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Description：Network Api
 * Created by：JasmineBen
 * Time：2017/10/30
 */
public interface GankApi {
    public static final String BASE_URL = "http://gank.io/api/";
    public static final String DATE_FORMAT_CONVERT = "yyyy-MM-dd'T'HH:mm:ss";

    enum GankDataType{
        DATA_TYPE_ALL("all"),DATA_TYPE_ANDROID("Android"),
        DATA_TYPE_IOS("iOS"), DATA_TYPE_WELFARE("福利"),
        DATA_TYPE_BREAK_VIDEO("休息视频"),DATA_TYPE_EXPAND_RESOURCE("拓展资源"),
        DATA_TYPE_FRONT( "前端");

        private String mDataType;

        GankDataType(String type){
            this.mDataType = type;
        }

        public String getDataType() {
            return mDataType;
        }

    }

    @GET("data/{type}/{pageSize}/{pageIndex}")
    Call<GankList> getGankList(@Path("type") String type, @Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);
}
