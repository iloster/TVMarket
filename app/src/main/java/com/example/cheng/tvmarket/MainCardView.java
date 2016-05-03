package com.example.cheng.tvmarket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.bean.ItemBean;
import com.example.cheng.http.DownloadCallBack;
import com.example.cheng.http.HttpUtil;
import com.example.cheng.task.MD5TaskRunnable;
import com.example.cheng.task.TaskPool;
import com.example.cheng.utils.FileUtils;
import com.example.cheng.utils.LogUtils;
import com.example.cheng.utils.MD5Utils;
import com.example.cheng.view.CircleProgressBar;

import java.io.IOException;
import java.util.List;

/**
 * Created by cheng on 16/4/11.
 */
public class MainCardView extends RecyclerView.Adapter {

    private String TAG = "MainCardView";
    private Context context;
    private List<ItemBean> itemBeans;
    public MainCardView(Context context,List<ItemBean> itemBeans){
        this.context = context;
        this.itemBeans = itemBeans;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder)holder;
        LogUtils.v(TAG, "position:" + position);
        viewHolder.nameTxt.setText(itemBeans.get(position).getApkName().toString());
        viewHolder.nameTxt.setText(itemBeans.get(position).getApkName().toString());
        viewHolder.authorTxt.setText("备注: "+itemBeans.get(position).getApkAuthor());
        viewHolder.sizeTxt.setText("大小: " + itemBeans.get(position).getApkSize() / (1024 * 1024) + "M");
        viewHolder.dataTxt.setText("日期: " + itemBeans.get(position).getApkDate());
        final MyViewHolder finalViewHolder = viewHolder;
        //itemBeans.get(position).setApkStatus(isApkExist(position));
        MD5TaskRunnable r = new MD5TaskRunnable(getFilePath(position), itemBeans.get(position).getApkMd5(), new MD5TaskRunnable.MD5CallBack() {
            @Override
            public void onCallBack(boolean status) {
                itemBeans.get(position).setApkStatus(status);
                if(status){
                    viewHolder.downloadBtn.setText("安装");
                    viewHolder.sizeTxt.setText("大小: "+itemBeans.get(position).getApkSize()/(1024*1024) + "M"+"(已下载)");
                }else{
                    viewHolder.downloadBtn.setText("下载");
                }
            }
        });
        TaskPool.getInstance().quene(r);

        viewHolder.downloadBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus) {
                    if(itemBeans.get(position).getApkStatus()){
                        viewHolder.downloadBtn.setText("点击OK安装");
                    }else {
                        viewHolder.downloadBtn.setText("点击OK下载");
                    }
                }else{
                    if(itemBeans.get(position).getApkStatus()){
                        viewHolder.downloadBtn.setText("安装");
                    }else {
                        viewHolder.downloadBtn.setText("下载");
                    }
                }

            }
        });
        if(position == 0){
            viewHolder.downloadBtn.requestFocus();
        }

        viewHolder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.v(TAG, "id:" + itemBeans.get(position).getApkName().toString());

                if(itemBeans.get(position).getApkStatus()){
                    installApk(getFilePath(position));
                    return ;
                }
                finalViewHolder.downloadBtn.setVisibility(View.INVISIBLE);
                finalViewHolder.progress.setVisibility(View.VISIBLE);
                //如果没有sdcard
//                String dir;
//                if(FileUtils.getSDCardStatus()) {
//                    dir = Environment.getExternalStorageDirectory().getPath() + "/" + context.getPackageName()+"/files";
//                }else {
//                    dir = context.getFilesDir().toString();
//                }
//                String path = "";
//                if (FileUtils.createDir(dir)) {
//                    path = dir + "/" + itemBeans.get(position).getApkName().toString();
//                }
                final String finalPath = getFilePath(position);
                HttpUtil.getInstance().download(itemBeans.get(position).getApkUrl(), finalPath, new DownloadCallBack() {
                    @Override
                    public void onProgress(int progress) {
                        finalViewHolder.progress.setProgress(progress);
                    }

                    @Override
                    public void onError() {
                        finalViewHolder.progress.setVisibility(View.INVISIBLE);
                        finalViewHolder.downloadBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "下载失败，请重新下载！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(String ret) {
                        finalViewHolder.progress.setVisibility(View.INVISIBLE);
                        finalViewHolder.downloadBtn.setVisibility(View.VISIBLE);
                        viewHolder.downloadBtn.setText("安装");
                        itemBeans.get(position).setApkStatus(true);
                        installApk(finalPath);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemBeans.size();
    }

    private String getFilePath(int position){
        String dir;
        if(FileUtils.getSDCardStatus()) {
            dir = Environment.getExternalStorageDirectory().getPath() + "/" + context.getPackageName()+"/files";
        }else {
            dir = context.getFilesDir().toString();
        }
        String path = "";
        if (FileUtils.createDir(dir)) {
            path = dir + "/" + itemBeans.get(position).getApkName().toString();
        }
        return path;
    }

    private boolean isApkExist(int position){
        if(MD5Utils.verify(getFilePath(position),itemBeans.get(position).getApkMd5())){
            return true;
        }
        return false;
    }
    private void installApk(String path){
        //第一步 添加权限
        String command     = "chmod 777 " + path;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        第三步：使用Intent 调用安装：
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        context.startActivity(intent);

    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView iconImg;
        public TextView nameTxt,authorTxt,sizeTxt,dataTxt;
        public Button downloadBtn;
        public CircleProgressBar progress;
        public MyViewHolder(View itemView) {
            super(itemView);

            iconImg = (ImageView)itemView.findViewById(R.id.iconImg);
            nameTxt = (TextView)itemView.findViewById(R.id.nameTxt);
            sizeTxt = (TextView)itemView.findViewById(R.id.sizeTxt);
            dataTxt = (TextView)itemView.findViewById(R.id.dataTxt);
            downloadBtn = (Button)itemView.findViewById(R.id.downloadBtn);
            progress = (CircleProgressBar)itemView.findViewById(R.id.progress);
            progress.setVisibility(View.INVISIBLE);
            authorTxt = (TextView)itemView.findViewById(R.id.authorTxt);
        }

        public Button getDownloadBtn(){
            return downloadBtn;
        }
    }
}
