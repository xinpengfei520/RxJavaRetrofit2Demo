package com.xpf.android.retrofit.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;


/**
 * Created by x-sir on 2018-08-04 :)
 * Function:HttpCookieManger
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */

public class HttpCookieManger implements CookieJar {

    private static Context mContext;
    private static PersistentCookieStore cookieStore;
    private static final String TAG = HttpCookieManger.class.getSimpleName();

    /**
     * Mandatory constructor for the HttpCookieManger
     */
    public HttpCookieManger(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(mContext);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

}
