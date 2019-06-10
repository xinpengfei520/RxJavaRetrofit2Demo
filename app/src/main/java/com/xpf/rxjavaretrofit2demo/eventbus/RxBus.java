package com.xpf.rxjavaretrofit2demo.eventbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by x-sir on 2019-06-08 :)
 * Function:静态内部类实现单例，保证线程安全
 */
public class RxBus {

    /**
     * Subject 既是 Observable 又是 Observer
     */
    private final Subject<Object> mBus;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object object) {
        mBus.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}
