package com.imdb.ui.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.view.View;

import com.imdb.api.AppRetrofit;
import com.imdb.di.AppComponent;
import com.imdb.di.AppModule;
import com.imdb.di.DaggerAppComponent;
import com.imdb.utility.AppUtils;
import com.imdb.utility.DialogUtil;
import com.imdb.utility.Lg;

import dagger.Module;

@Module
public class App extends Application {


    private static String TAG = App.class.getName();
    public static boolean isConnected;
    private BroadcastReceiver mNetStateChangeReceiver;
    public AppComponent mAppComponent;

    private static App mAppInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(getNetworkStateReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        isConnected = AppUtils.isNetworkAvailable(this);
        mAppInstance = this;
        mAppComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .appRetrofit(new AppRetrofit())
                .build();
    }

    public static boolean isConnected(View anyView, View.OnClickListener retryListener) {
        if (!isConnected) DialogUtil.showNoNetworkSnackBar(anyView, retryListener);
        return isConnected;
    }

    public static App getInstance() {
        return mAppInstance;
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(mNetStateChangeReceiver);
        super.onTerminate();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    private BroadcastReceiver getNetworkStateReceiver() {
        mNetStateChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isConnected = AppUtils.isNetworkAvailable(App.this);
                Lg.e("Network State", isConnected ? "Network Available" : "Network Unavailable");

            }
        };
        return mNetStateChangeReceiver;
    }


}