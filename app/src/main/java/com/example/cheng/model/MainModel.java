package com.example.cheng.model;

import com.example.cheng.http.CallBack;
import com.example.cheng.http.HttpUtil;

/**
 * Created by cheng on 16/4/2.
 */
public class MainModel {
    private String TAG = "MainModel";

    public void loadData(MainPresenterListener listener){
        final MainPresenterListener mListenter = listener;
        String service= "market.getList";
        HttpUtil.getInstance().enqueue(service, null, new CallBack() {
            @Override
            public void onError() {
                mListenter.onError();
            }

            @Override
            public void onSuccess(String ret) {

                mListenter.onSuccess(ret);
            }

        });
    }


}
