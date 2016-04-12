package com.example.cheng.tvmarket;

import com.example.cheng.bean.ItemBean;

import java.util.List;

/**
 * Created by cheng on 16/4/2.
 */
public interface IMainView {

    /**
     * 显示列表
     */
    public abstract void showList(List<ItemBean> itemBeans);

    /*
     * 显示loading
     */
    public abstract void showLoading();

    /**
     * 显示加载错误界面
     */
    public abstract void showError();
}
