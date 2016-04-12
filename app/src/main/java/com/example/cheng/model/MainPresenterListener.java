package com.example.cheng.model;

/**
 * Created by cheng on 16/4/2.
 */
public interface MainPresenterListener {
    public abstract void onError();
    public abstract void onSuccess(String ret);
}
