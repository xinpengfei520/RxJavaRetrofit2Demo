package com.xpf.android.retrofit;

import android.content.Context;

import com.xpf.android.retrofit.cookie.HttpCookieManger;
import com.xpf.android.retrofit.intercepter.HttpCacheInterceptor;
import com.xpf.android.retrofit.service.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:RetrofitHelper
 */
public class RetrofitHelper {

    private static final String BASE_URL = BuildConfig.HOST; // host
    private static final long DEFAULT_TIMEOUT = 10000L; // timeout millis
    private static final String TAG = RetrofitHelper.class.getSimpleName();

    private Retrofit retrofit;
    private OkHttpClient client;
    private Context mContext;

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    /**
     * private constructor.
     */
    private RetrofitHelper() {
        //HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(MyApplication.getIntstance(), new int[0], R.raw.ivms8700, STORE_PASS);
        client = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //.hostnameVerifier(HttpsUtils.getHostnameVerifier())
                //.addInterceptor(new HeaderInterceptor()) // 添加请求头
                //.addInterceptor(new LoggerInterceptor("===", true)) // 添加日志打印拦截器
                .cookieJar(new HttpCookieManger(mContext))
                .addNetworkInterceptor(new HttpCacheInterceptor(mContext))
                //.cache(new HttpCache(mContext).getCache())
                //.connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) // 添加 Gson 解析
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加rxJava
                .build();
    }

    /**
     * default service.
     *
     * @return
     */
    public ApiService createService() {
        return createService(ApiService.class);
    }

    /**
     * 这里返回一个泛型类，主要返回的是定义的接口类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clazz) {
        if (clazz == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(clazz);
    }
}
