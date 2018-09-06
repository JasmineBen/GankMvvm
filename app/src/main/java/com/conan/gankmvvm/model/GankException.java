package com.conan.gankmvvm.model;


/**
 * Description：Gank数据的管理类
 * Created by：JasmineBen
 * Time：2017/10/28
 */
public class GankException extends Exception {

    private boolean error;
    private String msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
