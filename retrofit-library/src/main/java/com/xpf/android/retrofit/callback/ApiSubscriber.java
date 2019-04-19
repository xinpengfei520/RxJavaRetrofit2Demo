package com.xpf.android.retrofit.callback;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:过滤 onComplete()
 */
public abstract class ApiSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onComplete() {

    }
}
