package com.xpf.android.retrofit.intercepter;

import android.util.Log;

import com.xpf.android.retrofit.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by x-sir on 2018/8/3 :)
 * Function:LoggerInterceptor
 */
public class LoggerInterceptor implements Interceptor {

    private static String TAG = "LoggerInterceptor";
    private boolean isDebug;

    public LoggerInterceptor(boolean isDebug) {
        this(TAG, isDebug);
    }

    public LoggerInterceptor(String tag, boolean isDebug) {
        this.isDebug = isDebug;
        TAG = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (BuildConfig.DEBUG || isDebug) {
            Log.i(TAG, String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }
        return chain.proceed(request);
    }
}
