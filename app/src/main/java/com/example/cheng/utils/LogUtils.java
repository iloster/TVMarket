package com.example.cheng.utils;

import android.util.Log;

/**
 * Created by cheng on 16/4/1.
 */
public class LogUtils {
    private static boolean DEBUG = true;

    public static void v(String tag,String msg){
        if(DEBUG&&msg!=null){
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(DEBUG&&msg!=null){
            Log.e(tag,msg);
        }
    }
}
