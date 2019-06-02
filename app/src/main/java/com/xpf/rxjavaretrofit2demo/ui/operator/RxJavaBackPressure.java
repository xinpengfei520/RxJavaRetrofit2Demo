package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by x-sir on 2016-12-21 :)
 * Function:RxJava 2.x 背压
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaBackPressure extends AppCompatActivity {

    private static final String TAG = "RxJavaBackPressure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_backpress);

        //backPressureWithErrorStrategy();
        //backPressureWithBufferStrategy();
        //backPressureWithDropStrategy();
        backPressureWithLatestStrategy();
    }

    /**
     * 背压是指在异步场景下，被观察者发送事件的速度远快于观察者处理的速度，从而导致下游的 buffer 溢出，这种现象叫做背压，
     * 背压必须是在异步的场景下才会出现，即被观察者和观察者处于不同的线程中。
     * RxJava 是基于 push 模型的，对于 pull 模型而言，当消费者请求数据的时候，如果生产者比较慢，则消费者会阻塞等待。
     * RxJava2.x 中，Observable 不再支持背压，而是改用 Flowable 来专门支持背压，默认队列大小为 128
     * <p>
     * RxJava2.x 的 5 种背压策略:
     * <p>
     * MISSING ： 表示通过 create 方法创建的 Flowable 没有指定背压策略，不会对通过 onNext 发射的数据做缓存或丢弃处理，
     * 需要下游通过背压操作符（onBackpressureBuffer()/onBackpressureDrop()/onBackpressureLatest()）
     * 指定背压策略；
     * <p>
     * ERROR ： 表示如果放入 Flowable 的异步缓存池中的数据超限了，则会抛出 MissingBackpressureException 异常
     * <p>
     * BUFFER ： 表示 Flowable 的异步缓存池同 Observable 的一样，没有固定大小，可以无限制添加数据，不会抛出
     * MissingBackpressureException 异常，但会导致 OOM
     * <p>
     * DROP ：表示如果 Flowable 的异步缓存池满了，则会丢掉将要放入缓存池中的数据
     * LATEST ：表示如果缓存池满了，会丢掉将要放入缓存池中的数据，这点与 Drop 一样，不同的是，不管缓存池的状态如何，
     * Latest 策略会将最后一条数据强行放入缓存池中
     */
    private void backPressureWithErrorStrategy() {
        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 128; i++) {
                emitter.onNext(i);
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer),
                        throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete()"));

        /*
         * 当 i < 129 时，报 ： create: could not emit value due to lack of requests
         * 因为 Flowable 默认的队列是 128，所以改成 128 就可以正常运行并打印出 0-127 的值
         */
    }

    private void backPressureWithBufferStrategy() {
        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; ; i++) {
                emitter.onNext(i);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer),
                        throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete()"));

        // 执行结果：会出现 ANR
    }

    private void backPressureWithDropStrategy() {
        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 129; i++) {
                emitter.onNext(i);
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer),
                        throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete()"));

        // 执行结果：打印 0-127，APP 不会 crash，第 128 会被丢弃，因为 Flowable 的内部队列已经满了
    }

    private void backPressureWithLatestStrategy() {
        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 1000; i++) {
                emitter.onNext(i);
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> LogUtil.i(TAG, "integer:" + integer),
                        throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete()"));

        // 执行结果：打印 0-127 和 999，因为 999 是最后一条数据，APP 也不会 crash
    }

}
