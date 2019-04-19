package com.xpf.android.retrofit.utils;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:线程调度
 */
public class RxThreadUtils {

    /**
     * 线程切换
     *
     * @return
     */
    public static <T> FlowableTransformer<T, T> getScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
