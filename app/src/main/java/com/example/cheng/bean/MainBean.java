package com.example.cheng.bean;

import java.util.List;

/**
 * Created by cheng on 16/4/2.
 */
public class MainBean implements HttpBean {
    private int ret;
    private String msg;
    private  List<ItemBean> data;

    public List<ItemBean> getData() {
        return data;
    }

    public void setData(List<ItemBean> data) {
        this.data = data;
    }

    @Override
    public int getRet() {
        return ret;
    }

    @Override
    public void setRet(int ret) {
        this.ret = ret;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
