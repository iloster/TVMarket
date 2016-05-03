package com.example.cheng.task;

import android.os.Handler;
import android.os.Looper;

import com.example.cheng.utils.MD5Utils;

import java.io.File;
import java.util.Objects;

/**
 * Created by mac on 16/4/19.
 */
public class MD5TaskRunnable implements Runnable{
    private String path;
    private String md5;
    private MD5CallBack md5CallBack;
    private Handler mHandler;
    public MD5TaskRunnable(String path,String md5,MD5CallBack md5CallBack){
        this.path = path;
        this.md5 = md5;
        this.md5CallBack = md5CallBack;
        mHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void run() {
        final boolean status = MD5Utils.verify(path,md5);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                md5CallBack.onCallBack(status);
            }
        });

    }


    public interface MD5CallBack{
        abstract void onCallBack(boolean status);
    }
}
