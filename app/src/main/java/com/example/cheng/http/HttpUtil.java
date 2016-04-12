package com.example.cheng.http;



import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.cheng.bean.HttpBean;
import com.example.cheng.bean.MainBean;
import com.example.cheng.config.Constant;
import com.example.cheng.utils.LogUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cheng on 16/3/31.
 */
public class HttpUtil {
    private String TAG = "HttpUtil";
    private static String url= Constant.HTTP_URL;
    private OkHttpClient mOkHttpClient;

    private static final int HTTP_RET_SUCCESS = 0;
    private static final int HTTP_RET_ERROR = 1;

    private Handler mDelivery;

    private static HttpUtil mInstance = null;
    private Gson gson = new Gson();

    public HttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
    }
    public static HttpUtil getInstance(){
        if (mInstance == null)
        {
            synchronized (HttpUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new HttpUtil();
                }
            }
        }
        return mInstance;
    }

    public String excute(String service,String param){
        RequestBody formBody;
        if(param!=null) {
            formBody = new FormBody.Builder()
                    .add("service", service)
                    .add("api", param).build();
        }else{
            formBody = new FormBody.Builder()
                    .add("service", service).build();
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            return mOkHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * @param service  jie 接口
     * @param param  请求参数 为一个json字符串
     * @param callback  请求回调
     */
    public void enqueue(String service, String param,CallBack callback){
        RequestBody formBody;
        if(param!=null) {
            formBody = new FormBody.Builder()
                    .add("service", service)
                    .add("api", param).build();
        }else{
            formBody = new FormBody.Builder()
                    .add("service", service).build();
        }
        Log.v("ZedLi","service:"+service+"|"+"api:"+param);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        final CallBack mCallBack = callback;

        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailure(call, mCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    sendResponse(call, response.body().string(), mCallBack);
                } else {
                    sendFailure(call, mCallBack);
                }
            }
        });
    }

    /**
     *
     * @param url  下载链接
     * @param path  安装路径
     * @param callBack  回调
     */
    public void download(String url,String path,DownloadCallBack callBack){
        Request request = new Request.Builder().url(url).build();
        final String mPath = path;
        final DownloadCallBack mCallBack = callBack;
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailure(call,mCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int len;
                byte[] buf = new byte[2048];
                InputStream inputStream = response.body().byteStream();
                long fileLength = response.body().contentLength();
                long downloadLength = 0;
                //可以在这里自定义路径
                File file = new File(mPath);
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                while(downloadLength<fileLength){
                    len = inputStream.read(buf);
                    fileOutputStream.write(buf,0,len);
                    downloadLength += len;
                    sendProgress(call,(int)(downloadLength*100/fileLength),mCallBack);
                }
//                while ((len = inputStream.read(buf)) != -1) {
//                    fileOutputStream.write(buf, 0, len);
//                }

                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                sendResponse(call,"",mCallBack);
            }
        });
    }

    private void sendFailure(Call call, final CallBack callback){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                LogUtils.v(TAG,"ret:error");
                callback.onError();
            }
        });
    }

    private void sendResponse(Call call,final String ret, final CallBack callback){
         mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(ret);
            }
        });
    }

    private void sendProgress(Call call,final int progress,final DownloadCallBack callBack){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callBack.onProgress(progress);
            }
        });
    }

}
