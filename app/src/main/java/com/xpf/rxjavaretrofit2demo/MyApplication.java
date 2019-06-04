package com.xpf.rxjavaretrofit2demo;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.safframework.cache.Cache;

/**
 * Created by xpf on 2017/12/28 :)
 * Function:
 */
public class MyApplication extends Application {

    private static Context mContext;
    private static MyApplication instance;
    public static Cache cache;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        // init logger adapter
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
