package com.xpf.rxjavaretrofit2demo.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.safframework.lifecycle.RxLifecycle;

import io.reactivex.ObservableTransformer;

/**
 * Created by x-sir on 2019-06-05 :)
 * Function:
 */
public class RxUtils {

    /**
     * 对 RxView 的绑定事件，封装了防止重复点击和 RxLifecycle 的生命周期，防止内存泄露
     *
     * @param targetActivity 目标 Activity
     * @return Observable transformer
     */
    public static ObservableTransformer useRxViewTransformer(final AppCompatActivity targetActivity) {
        return upstream ->
                upstream
                        .compose(RxJavaUtils.INSTANCE.preventDuplicateClicksTransformer())
                        .compose(RxLifecycle.bind(targetActivity).toLifecycleTransformer());
    }
}
