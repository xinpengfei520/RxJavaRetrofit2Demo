package com.xpf.android.mvvm.dagger2

import com.xpf.android.mvvm.MyApplication
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by x-sir on 2019-10-16 :)
 * Function:
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    val okhttpClient: OkHttpClient

    fun injectApp(application: MyApplication)
}