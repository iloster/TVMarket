package com.example.cheng.tvmarket;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cheng.bean.ItemBean;
import com.example.cheng.http.CallBack;
import com.example.cheng.http.DownloadCallBack;
import com.example.cheng.http.HttpUtil;
import com.example.cheng.presenter.MainPresenter;
import com.example.cheng.utils.LogUtils;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends ActionBarActivity implements IMainView{

    private String TAG = "MainActivity";
    ///控件
    private ListView mListView;
    private ProgressBarCircularIndeterminate mLoading; //loading控件
    private RecyclerView mRecyclerView;
    private RelativeLayout mErrorLayout;

    private MainPresenter mainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mainPresenter = new MainPresenter(this);
        init();
    }

    private void findViews(){
//        mListView = (ListView)findViewById(R.id.listView);
        mLoading = (ProgressBarCircularIndeterminate)findViewById(R.id.loading);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mErrorLayout = (RelativeLayout)findViewById(R.id.errorLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void init(){
        mainPresenter.showLoading();
        mainPresenter.showList();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            init();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showList(List<ItemBean> itemBeans) {
        mLoading.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
//        CardView cardView = new CardView(this,itemBeans);
//        mListView.setAdapter(cardView);
        if(itemBeans.size()==0){
            showError();
        }else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            MainCardView cardView = new MainCardView(this, itemBeans);
            mRecyclerView.setAdapter(cardView);
        }

    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        mLoading.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }
}
