package com.example.cheng.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.RandomAccess;

/**
 * Created by cheng on 16/4/1.
 */
public class FileUtils {
    private static String TAG = "FileUtils";
    static final private String FILE_NAME = "boyaa.xml";
    /**
     *
     * @return true:表示有sdcard  false:表示没有sdcard
     */
    public static boolean getSDCardStatus(){
        if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            //如果有sd卡
            File sdCardDir = Environment.getExternalStorageDirectory();
            File targetFile;
            try {
                targetFile = new File(sdCardDir.getCanonicalPath() + '/'+ FILE_NAME);
                RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
                raf.seek(targetFile.length());
                raf.write("http//:www.boyaa.com".getBytes());
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
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
            LogUtils.e(TAG, e.toString());
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
            LogUtils.e(TAG, e.toString());
            return false;
        }
    }


}
