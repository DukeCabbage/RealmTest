package com.cabbage.realmtest;

import android.app.Application;

/**
 * Created by lwang on 06/07/2016.
 */
public class MyApp extends Application {
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
