package com.example.cheng.bean;

import java.util.List;

/**
 * Created by cheng on 16/4/2.
 */
public interface HttpBean {

    /**
     * ret : 200
     * data : [{"apkName":"TVGame-世博云","iconurl":"http://www.baidu.com","apkDate":1459591638,"apkAuthor":"ZedLi","apkUrl":"http://www.baidu.com"},{"apkName":"TVGame-鹏博士","iconurl":"http://www.baidu.com","apkDate":1459591638,"apkAuthor":"黄国权","apkUrl":"http://www.baidu.com"}]
     * msg :
     */

//    private int ret;
//    private String msg;
    /**
     * apkName : TVGame-世博云
     * iconurl : http://www.baidu.com
     * apkDate : 1459591638
     * apkAuthor : ZedLi
     * apkUrl : http://www.baidu.com
     */


    public abstract int getRet();
    public abstract void setRet(int ret);

    public abstract String getMsg();

    public abstract void setMsg(String msg);

}
