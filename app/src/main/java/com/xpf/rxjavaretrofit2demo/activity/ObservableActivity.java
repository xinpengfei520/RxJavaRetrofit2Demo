package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Hot & Cold Observable exercise
 */
public class ObservableActivity extends AppCompatActivity {

    private static final String TAG = "ObservableActivity";
    private Consumer<Long> subscribe1;
    private Consumer<Long> subscribe2;
    private Consumer<Long> subscribe3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);

        initObserver();

//        coldObservable();

//        coldObservableConvertToHotObservable();

//        subject();

        hotObservableConvertToColdObservable();
    }

    private void hotObservableConvertToColdObservable() {
        ConnectableObservable<Long> connectableObservable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                Observable.interval(800, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread()).publish();

        connectableObservable.connect();

        Observable<Long> observable = connectableObservable.refCount();

        Disposable disposable1 = observable.subscribe(subscribe1);
        Disposable disposable2 = observable.subscribe(subscribe2);

        Disposable disposable3 = observable.subscribe(subscribe3);

        threadSleep(1000);

        // 模拟部分取消
        disposable1.dispose();
        disposable2.dispose();

        LogUtil.i(TAG, "重新开始数据流...");

        disposable1 = observable.subscribe(subscribe1);
        disposable2 = observable.subscribe(subscribe2);
    }

    private void subject() {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread());

        PublishSubject<Long> subject = PublishSubject.create();
        observable.subscribe(subject);

        subject.subscribe(subscribe1);
        subject.subscribe(subscribe2);

        threadSleep(500);

        subject.subscribe(subscribe3);
    }

    private void initObserver() {
        subscribe1 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                LogUtil.i(TAG, "subscribe1===" + aLong);
            }
        };

        subscribe2 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                LogUtil.i(TAG, "subscribe2===" + aLong);
            }
        };

        subscribe3 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                LogUtil.i(TAG, "subscribe3===" + aLong);
            }
        };
    }

    private void coldObservableConvertToHotObservable() {
        /*
         * ConnectableObservable & push 是 Hot Observable
         */
        ConnectableObservable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread()).publish();

        observable.connect();

        observable.subscribe(subscribe1);
        observable.subscribe(subscribe2);

        threadSleep(500);

        observable.subscribe(subscribe3);

        threadSleep(1000);
    }

    private void coldObservable() {
        /*
         * create 创建的是 cold Observable
         */
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread());

        observable.subscribe(subscribe1);
        observable.subscribe(subscribe2);

        threadSleep(1000);
    }

    private void threadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
