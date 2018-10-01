package com.xpf.android.retrofit.cache;

import android.content.Context;
import android.util.Log;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by x-sir on 2018/8/4 :)
 * Function:HttpCache
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class HttpCache {

    private Cache cache;
    private File cacheDir;
    private Context mContext;
    private static final String TAG = HttpCache.class.getSimpleName();

    public HttpCache(Context context) {
        this.mContext = context;
    }

    public Cache getCache() {
        setCache();
        return cache;
    }

    private void setCache() {
        if (cacheDir == null) {
            cacheDir = new File(mContext.getCacheDir(), "HttpCache");
        }
        try {
            if (cache == null) {
                cache = new Cache(cacheDir, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Could not create HttpCache !");
        }
    }
}
