package com.xpf.rxjavaretrofit2demo.utils

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by x-sir on 2019-06-02 :)
 * Function:RxJava2.x 线程切换工具类
 */
object RxJavaUtils {

    /**
     * Observable 切换到主线程
     */
    fun <T> observableToMain(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * Flowable 切换到主线程
     */
    fun <T> flowableToMain(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 防止重复点击的 Transformer
     */
    fun <T> preventDuplicateClicksTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.throttleFirst(1000, TimeUnit.MILLISECONDS)
        }
    }
}
