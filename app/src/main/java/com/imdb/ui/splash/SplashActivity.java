package com.imdb.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.imdb.R;
import com.imdb.databinding.ActivitySplashBinding;
import com.imdb.ui.base.BaseActivity;
import com.imdb.ui.home.HomeActivity;
import com.imdb.ui.login.LoginActivity;


public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIMEOUT = 2000;
    private ActivitySplashBinding mSplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPrefMgr.isLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
