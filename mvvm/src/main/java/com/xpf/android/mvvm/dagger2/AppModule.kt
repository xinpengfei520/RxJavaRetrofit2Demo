package com.xpf.android.mvvm.dagger2

import com.xpf.android.mvvm.MyApplication
import com.xpf.android.mvvm.utils.HttpsUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

/**
 * Created by x-sir on 2019-10-16 :)
 * Function:
 */
@Module
class AppModule(internal var application: MyApplication) {

    @Singleton
    @Provides
    fun provideApplication(): MyApplication {
        return application
    }

    @Singleton
    @Provides
    fun providerOkHttpClient(): OkHttpClient {
        val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)
        return OkHttpClient.Builder()
                // lambda 表达式要在中括号外面加上类型
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
    }
}
