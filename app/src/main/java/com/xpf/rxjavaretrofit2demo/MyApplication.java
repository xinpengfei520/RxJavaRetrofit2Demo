package com.xpf.rxjavaretrofit2demo;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.safframework.cache.Cache;

/**
 * Created by xpf on 2017/12/28 :)
 * Function:
 */
public class MyApplication extends MultiDexApplication {

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
