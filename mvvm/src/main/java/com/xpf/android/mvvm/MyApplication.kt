package com.xpf.android.mvvm

import android.app.Application
import com.xpf.android.mvvm.dagger2.AppModule
import com.xpf.android.mvvm.dagger2.DaggerAppComponent
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class MyApplication : Application() {

    companion object {
        var instance: MyApplication? = null
    }

    @JvmField
    @Inject
    var client: OkHttpClient? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        DaggerAppComponent.builder().appModule(AppModule(this)).build().injectApp(this)
    }
}
