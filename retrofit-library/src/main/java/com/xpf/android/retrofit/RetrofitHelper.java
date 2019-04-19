package com.xpf.android.retrofit;

import android.content.Context;

import com.xpf.android.retrofit.intercepter.LoggerInterceptor;
import com.xpf.android.retrofit.service.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:RetrofitHelper singleton class.
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RetrofitHelper {

    private static final String TAG = RetrofitHelper.class.getSimpleName();
    /**
     * host ip
     */
    private static final String BASE_URL = BuildConfig.HOST;
    /**
     * timeout millis
     */
    private static final long DEFAULT_TIMEOUT = 10000L;
    private Retrofit mRetrofit;
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
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkhttpClient() {
        // cache url
//        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
//        // 10 MiB
//        int cacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(MyApplication.getIntstance(), new int[0], R.raw.ivms8700, STORE_PASS);
        return new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //.hostnameVerifier(HttpsUtils.getHostnameVerifier())
                // 添加请求头
                //.addInterceptor(new HeaderInterceptor())
                // 添加日志打印拦截器
                .addInterceptor(new LoggerInterceptor(true))
                //.addInterceptor(interceptor)
                //.cookieJar(new HttpCookieManger(mContext))
                //.addNetworkInterceptor(new HttpCacheInterceptor(mContext))
                //.cache(new HttpCache(mContext).getCache())
                //.connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，这里是 8 个，和每个保持时间为 15s
                //.cache(cache)
                .build();
    }

//    private Interceptor interceptor = chain -> {
//
//        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//        cacheBuilder.maxStale(365, TimeUnit.DAYS);
//        CacheControl cacheControl = cacheBuilder.build();
//
//        Request request = chain.request();
//        if (!NetworkUtil.isNetworkAvailable(mContext)) {
//            request = request.newBuilder()
//                    .cacheControl(cacheControl)
//                    .build();
//
//        }
//        Response originalResponse = chain.proceed(request);
//        if (NetworkUtil.isNetworkAvailable(mContext)) {
//            // read from cache
//            int maxAge = 0;
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public ,max-age=" + maxAge)
//                    .build();
//        } else {
//            // tolerate 4-weeks stale
//            int maxStale = 60 * 60 * 24 * 28;
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
//    };

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
        return mRetrofit.create(clazz);
    }
}
