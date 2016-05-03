package com.example.cheng.tvmarket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.bean.ItemBean;
import com.example.cheng.http.DownloadCallBack;
import com.example.cheng.http.HttpUtil;
import com.example.cheng.utils.FileUtils;
import com.example.cheng.utils.LogUtils;
import com.example.cheng.view.CircleProgressBar;

import java.io.IOException;
import java.util.List;

/**
 * Created by cheng on 16/4/2.
 */
public class CardView extends BaseAdapter{

    private String TAG = "CardView";
    private List<ItemBean> itemBeans;
    private Context context;
    public CardView(Context context,List<ItemBean> itemBeans){
        this.itemBeans = itemBeans;
        this.context = context;
    }
    @Override
    public int getCount() {
        return itemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return itemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_item,null);

            viewHolder = new ViewHolder();
            viewHolder.nameTxt = (TextView)convertView.findViewById(R.id.nameTxt);
            viewHolder.iconImg = (ImageView)convertView.findViewById(R.id.iconImg);
            viewHolder.authorTxt = (TextView)convertView.findViewById(R.id.authorTxt);
            viewHolder.sizeTxt = (TextView)convertView.findViewById(R.id.sizeTxt);
            viewHolder.dataTxt = (TextView)convertView.findViewById(R.id.dataTxt);
            viewHolder.downloadBtn = (Button)convertView.findViewById(R.id.downloadBtn);
            viewHolder.progress = (CircleProgressBar)convertView.findViewById(R.id.progress);
            viewHolder.progress.setVisibility(View.INVISIBLE);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTxt.setText(itemBeans.get(position).getApkName().toString());
        viewHolder.authorTxt.setText("上传人: "+itemBeans.get(position).getApkAuthor());
        viewHolder.sizeTxt.setText("大    小: "+itemBeans.get(position).getApkSize()/1024 + "M");
        viewHolder.dataTxt.setText("日    期: " + itemBeans.get(position).getApkDate());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.v(TAG, "id:" + itemBeans.get(position).getApkName().toString());
                finalViewHolder.downloadBtn.setVisibility(View.INVISIBLE);
                finalViewHolder.progress.setVisibility(View.VISIBLE);
                String dir = Environment.getExternalStorageDirectory().getPath() + "/" + context.getPackageName();
                String path = "";
                if (FileUtils.createDir(dir)) {
                    path = dir + "/" + itemBeans.get(position).getApkName().toString() + ".apk";
                }
                final String finalPath = path;
                HttpUtil.getInstance().download(itemBeans.get(position).getApkUrl(), path, new DownloadCallBack() {
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
                        installApk(finalPath);
                    }
                });
            }
        });
        return convertView;
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
        intent.setDataAndType(Uri.parse("file://" + path),"application/vnd.android.package-archive");
        context.startActivity(intent);

    }
    private class ViewHolder{
        private ImageView iconImg;
        private TextView nameTxt,authorTxt,sizeTxt,dataTxt;
        private Button downloadBtn;
        private CircleProgressBar progress;
    }
}
