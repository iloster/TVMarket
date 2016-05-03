package com.example.cheng.tvmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        toMain();
    }

    private void toMain(){
       new Handler().postDelayed(new Runnable() {

           @Override
           public void run() {
               Intent intent = new Intent(SplashActivity.this, MainActivity.class);
               SplashActivity.this.startActivity(intent);
               SplashActivity.this.finish();
           }
       }, 3000);
    }
}
