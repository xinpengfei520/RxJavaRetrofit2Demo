package com.xpf.rxjavaretrofit2demo;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by xpf on 2017/12/28 :)
 * Function:
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // init logger adapter
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getAppContext() {
        return mContext;
    }
}
