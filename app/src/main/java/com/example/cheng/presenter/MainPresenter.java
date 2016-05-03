package com.example.cheng.presenter;

import com.example.cheng.bean.MainBean;
import com.example.cheng.model.MainModel;
import com.example.cheng.model.MainPresenterListener;
import com.example.cheng.tvmarket.IMainView;
import com.example.cheng.utils.LogUtils;
import com.google.gson.Gson;

/**
 * Created by cheng on 16/4/2.
 */
public class MainPresenter {
    private String TAG = "MainPresenter";
    private IMainView mMainView;
    private MainModel mMainModel;
    private Gson gson = new Gson();
    public MainPresenter(IMainView mainView){
        mMainView = mainView;
        mMainModel = new MainModel();
    }

    public void showList(){
        mMainModel.loadData(new MainPresenterListener() {
            @Override
            public void onError() {
                mMainView.showError();
            }

            @Override
            public void onSuccess(String ret) {
                LogUtils.v(TAG, "ret:" + ret);
                MainBean mainBean = gson.fromJson(ret,MainBean.class);
                mMainView.showList(mainBean.getData());
            }
        });

    }

    public void showLoading(){
        mMainView.showLoading();
    }
}
