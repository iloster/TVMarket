package com.example.cheng.tvmarket;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.bean.ItemBean;
import com.example.cheng.presenter.MainPresenter;
import com.example.cheng.utils.LogUtils;
import com.example.cheng.utils.NetUtils;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements IMainView{

    private String TAG = "MainActivity";
    ///控件
    private ListView mListView;
    private ProgressBarCircularIndeterminate mLoading; //loading控件
    private RecyclerView mRecyclerView;
    private RelativeLayout mErrorLayout,mMainLayout;

    private MainPresenter mainPresenter;
    private MainCardView mMainCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon);
        findViews();
        init();
    }

    private void findViews(){
//        mListView = (ListView)findViewById(R.id.listView);
        mLoading = (ProgressBarCircularIndeterminate)findViewById(R.id.loading);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mErrorLayout = (RelativeLayout)findViewById(R.id.errorLayout);
        mMainLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void init(){
        showActionBar();
        mainPresenter = new MainPresenter(this);
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

    public void showActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.tv);
//        View titleView = LayoutInflater.from(this).inflate(R.layout.activity_main_titile,null);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.activity_main_titile);
        View view = actionBar.getCustomView();
        TextView ssidTxt = (TextView)view.findViewById(R.id.ssidTxt);
        TextView ipTxt = (TextView)view.findViewById(R.id.ipTxt);
        Map<String,String> map = NetUtils.getWifiInfo(this);
        if(map.size()>0){
            ssidTxt.setText(map.get("ssid").toString());
            ipTxt.setText(map.get("ip").toString());
        }else{
            ssidTxt.setText("未连接网络");
        }

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
            if(isScreenChange()){
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
            }else{
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

            mMainCardView = new MainCardView(this, itemBeans);
            mRecyclerView.setAdapter(mMainCardView);
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

    /**
     * true 为横屏
     * false 为竖屏
     * @return
     */
    public boolean isScreenChange(){
        Configuration configuration = this.getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true;
        }
        return false;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            //横屏
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }else{
            //竖屏
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View view = mMainLayout.findFocus();
        int downId = mMainLayout.getNextFocusDownId();
        int upId = mMainLayout.getNextFocusUpId();
        int leftId = mMainLayout.getNextFocusDownId();
        int rightId = mMainLayout.getNextFocusRightId();
        View view1= null;
        if(view == null || view.getId() == R.id.recyclerView){
            view1 = mRecyclerView.getChildAt(0);
            if(view1!=null)
                view1.requestFocus();
        }


        return super.onKeyDown(keyCode, event);
    }
}
