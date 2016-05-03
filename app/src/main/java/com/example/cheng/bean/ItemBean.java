package com.example.cheng.bean;

/**
 * Created by cheng on 16/4/2.
 */
public class ItemBean {

    private String iconurl = "";  //icon的链接
    private String apkName = "";     //apk的名字
    private String apkDate;      //上传时间
    private String apkAuthor = "";    //上传人
    private String apkUrl = "";     //下载链接
    private String apkMd5=""; //apk的md5值
    private long apkSize;
    private boolean apkStatus;
    public void setIconUrl(String iconurl){
        this.iconurl = iconurl;
    }

    public String getIconUrl(){
        return this.iconurl;
    }

    public void setApkName(String apkName){
        this.apkName = apkName;
    }
    public String getApkName(){
        return this.apkName;
    }

    public void setApkDate(String apkDate){
        this.apkDate = apkDate;
    }

    public String getApkDate(){
        return this.apkDate;
    }

    public void setApkAuthor(String apkAuthor){
        this.apkAuthor = apkAuthor;
    }

    public String getApkAuthor(){
        return this.apkAuthor;
    }

    public void setApkUrl(String apkUrl){
        this.apkUrl = apkUrl;
    }

    public String getApkUrl(){
        return this.apkUrl;
    }

    public void setApkSize(long apkSize){
        this.apkSize = apkSize;
    }
    public long getApkSize(){
        return this.apkSize;
    }
    public void setApkMd5(String apkMd5){
        this.apkMd5 = apkMd5;
    }
    public String getApkMd5(){
        return this.apkMd5;
    }

    public void setApkStatus(boolean apkStatus){
        this.apkStatus = apkStatus;
    }
    public boolean getApkStatus(){
        return this.apkStatus;
    }
}
