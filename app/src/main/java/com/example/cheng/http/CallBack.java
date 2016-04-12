package com.example.cheng.http;


/**
 * Created by cheng on 16/3/31.
 */
public interface CallBack {

    public abstract void onError();

    public abstract void onSuccess(String ret);

}
