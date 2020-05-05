package com.imdb.prefutil;

import android.app.Application;

import com.imdb.ui.App;


public class PreferenceManager {

    private final PreferenceUtil mPref;
    private static PreferenceManager sInstance;


    private PreferenceManager(Application application) {
        mPref = new PreferenceUtil(application);
    }

    public static void init(Application application) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(application);
        }
    }

    public static PreferenceManager get() {
        init(App.getInstance());
        return sInstance;
    }

    public boolean isLoggedIn() {
        return mPref.getBooleanValue(PrefConstants.IS_LOGGED_IN);
    }


    public void clear() {
        mPref.clear();
    }

    public void clearAll() {
        mPref.clearAll();
    }


    public void setIsLogIn(boolean isLoggedIn) {
        mPref.save(PrefConstants.IS_LOGGED_IN, isLoggedIn);
    }

}
