package com.example.cheng.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by cheng on 16/4/1.
 */
public class FileUtils {
    private static String TAG = "FileUtils";

    /**
     *
     * @return true:表示有sdcard  false:表示没有sdcard
     */
    public boolean getSDCardStatus(){
        if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

    /**
     * 创建一个目录
     * @param dir
     * @return
     */
    public static boolean createDir(String dir){
        try {
            File dirFile = new File(dir);
            dirFile.mkdirs();
            return true;
        }catch (Exception e){
            LogUtils.e(TAG,e.toString());
            return false;
        }
    }

    /**
     *  创建一个新的文件
     * @param filePath
     * @return
     */
    public boolean createFile(String filePath){
        try{
            File file = new File(filePath);
            file.createNewFile();
            return true;
        } catch (IOException e) {
            LogUtils.e(TAG,e.toString());
            return false;
        }
    }


}
