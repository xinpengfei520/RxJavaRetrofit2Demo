package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

/**
 * Created by x-sir on 2019-05-28 :)
 * Function:RxJava2.x merge operator
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJava2MergeOperator extends AppCompatActivity {

    private static final String TAG = "RxJava2MergeOperator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2_merge_operator);

        //merge();
        //zip();
        //combineLatest();
        //join();
        //startWith();
        //connect();
        //refCount();
        replay();
    }

    private void replay() {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(6);
        ConnectableObservable<Long> connectableObservable = obs.replay();
        connectableObservable.connect();

        connectableObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.i(TAG, "onNext1:" + aLong + ",time:" + sdf.format(new Date()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "onComplete():1");
            }
        });

        connectableObservable.delaySubscription(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.i(TAG, "onNext2:" + aLong + ",time:" + sdf.format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "onComplete():2");
                    }
                });


        //        2019-05-29 22:59:56.616 24935-24974/? I/RxJava2MergeOperator: onNext1:0,time:22:59:56
        //        2019-05-29 22:59:57.612 24935-24974/? I/RxJava2MergeOperator: onNext1:1,time:22:59:57
        //        2019-05-29 22:59:58.613 24935-24974/? I/RxJava2MergeOperator: onNext1:2,time:22:59:58
        //        2019-05-29 22:59:58.614 24935-24975/? I/RxJava2MergeOperator: onNext2:0,time:22:59:58
        //        2019-05-29 22:59:58.614 24935-24975/? I/RxJava2MergeOperator: onNext2:1,time:22:59:58
        //        2019-05-29 22:59:58.614 24935-24975/? I/RxJava2MergeOperator: onNext2:2,time:22:59:58
        //        2019-05-29 22:59:59.611 24935-24974/? I/RxJava2MergeOperator: onNext1:3,time:22:59:59
        //        2019-05-29 22:59:59.611 24935-24974/? I/RxJava2MergeOperator: onNext2:3,time:22:59:59
        //        2019-05-29 23:00:00.612 24935-24974/? I/RxJava2MergeOperator: onNext1:4,time:23:00:00
        //        2019-05-29 23:00:00.613 24935-24974/? I/RxJava2MergeOperator: onNext2:4,time:23:00:00
        //        2019-05-29 23:00:01.613 24935-24974/? I/RxJava2MergeOperator: onNext1:5,time:23:00:01
        //        2019-05-29 23:00:01.613 24935-24974/? I/RxJava2MergeOperator: onNext2:5,time:23:00:01
        //        2019-05-29 23:00:01.613 24935-24974/? I/RxJava2MergeOperator: onComplete():1
        //        2019-05-29 23:00:01.613 24935-24974/? I/RxJava2MergeOperator: onComplete():2
    }

    private void refCount() {

        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(6);

        ConnectableObservable<Long> connectableObservable = obs.publish();

        Observable<Long> refCount = connectableObservable.refCount();

        connectableObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.i(TAG, "onNext1:" + aLong + ",time:" + sdf.format(new Date()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        connectableObservable.delaySubscription(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.i(TAG, "onNext2:" + aLong + ",time:" + sdf.format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        refCount.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.i(TAG, "onNext1:" + aLong + ",time:" + sdf.format(new Date()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        refCount.delaySubscription(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.i(TAG, "onNext2:" + aLong + ",time:" + sdf.format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //        2019-05-29 22:56:00.150 24785-24823/? I/RxJava2MergeOperator: onNext1:0,time:22:56:00
        //        2019-05-29 22:56:00.151 24785-24823/? I/RxJava2MergeOperator: onNext1:0,time:22:56:00
        //        2019-05-29 22:56:01.148 24785-24823/? I/RxJava2MergeOperator: onNext1:1,time:22:56:01
        //        2019-05-29 22:56:01.148 24785-24823/? I/RxJava2MergeOperator: onNext1:1,time:22:56:01
        //        2019-05-29 22:56:02.148 24785-24823/? I/RxJava2MergeOperator: onNext1:2,time:22:56:02
        //        2019-05-29 22:56:02.148 24785-24823/? I/RxJava2MergeOperator: onNext1:2,time:22:56:02
        //        2019-05-29 22:56:02.148 24785-24823/? I/RxJava2MergeOperator: onNext2:2,time:22:56:02
        //        2019-05-29 22:56:03.149 24785-24823/? I/RxJava2MergeOperator: onNext1:3,time:22:56:03
        //        2019-05-29 22:56:03.149 24785-24823/? I/RxJava2MergeOperator: onNext1:3,time:22:56:03
        //        2019-05-29 22:56:03.149 24785-24823/? I/RxJava2MergeOperator: onNext2:3,time:22:56:03
        //        2019-05-29 22:56:03.150 24785-24823/? I/RxJava2MergeOperator: onNext2:3,time:22:56:03
        //        2019-05-29 22:56:04.150 24785-24823/? I/RxJava2MergeOperator: onNext1:4,time:22:56:04
        //        2019-05-29 22:56:04.151 24785-24823/? I/RxJava2MergeOperator: onNext1:4,time:22:56:04
        //        2019-05-29 22:56:04.151 24785-24823/? I/RxJava2MergeOperator: onNext2:4,time:22:56:04
        //        2019-05-29 22:56:04.151 24785-24823/? I/RxJava2MergeOperator: onNext2:4,time:22:56:04
        //        2019-05-29 22:56:05.150 24785-24823/? I/RxJava2MergeOperator: onNext1:5,time:22:56:05
        //        2019-05-29 22:56:05.150 24785-24823/? I/RxJava2MergeOperator: onNext1:5,time:22:56:05
        //        2019-05-29 22:56:05.150 24785-24823/? I/RxJava2MergeOperator: onNext2:5,time:22:56:05
    }

    private void connect() {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Observable<Long> obs = Observable.interval(1, TimeUnit.SECONDS).take(6);

        ConnectableObservable<Long> connectableObservable = obs.publish();

        connectableObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.i(TAG, "onNext1:" + aLong + ",time:" + sdf.format(new Date()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        connectableObservable.delaySubscription(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.i(TAG, "onNext2:" + aLong + ",time:" + sdf.format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        connectableObservable.connect();

        //        2019-05-29 22:50:56.043 24526-24572/? I/RxJava2MergeOperator: onNext1:0,time:22:50:56
        //        2019-05-29 22:50:57.041 24526-24572/? I/RxJava2MergeOperator: onNext1:1,time:22:50:57
        //        2019-05-29 22:50:58.041 24526-24572/? I/RxJava2MergeOperator: onNext1:2,time:22:50:58
        //        2019-05-29 22:50:58.041 24526-24572/? I/RxJava2MergeOperator: onNext2:2,time:22:50:58
        //        2019-05-29 22:50:59.041 24526-24572/? I/RxJava2MergeOperator: onNext1:3,time:22:50:59
        //        2019-05-29 22:50:59.042 24526-24572/? I/RxJava2MergeOperator: onNext2:3,time:22:50:59
        //        2019-05-29 22:51:00.040 24526-24572/? I/RxJava2MergeOperator: onNext1:4,time:22:51:00
        //        2019-05-29 22:51:00.041 24526-24572/? I/RxJava2MergeOperator: onNext2:4,time:22:51:00
        //        2019-05-29 22:51:01.041 24526-24572/? I/RxJava2MergeOperator: onNext1:5,time:22:51:01
        //        2019-05-29 22:51:01.042 24526-24572/? I/RxJava2MergeOperator: onNext2:5,time:22:51:01
    }

    private void startWith() {
        Observable.just("Hello Java", "Hello Kotlin", "Hello Scala")
                .startWithArray("Hello Groovy", "Hello C++")
                .startWith(Observable.just("Hello RxJava"))
                .subscribe(integer -> LogUtil.i(TAG, "onNext:" + integer));

        //        2019-05-29 22:41:33.513 24154-24154/? I/RxJava2MergeOperator: onNext:Hello RxJava
        //        2019-05-29 22:41:33.513 24154-24154/? I/RxJava2MergeOperator: onNext:Hello Groovy
        //        2019-05-29 22:41:33.513 24154-24154/? I/RxJava2MergeOperator: onNext:Hello C++
        //        2019-05-29 22:41:33.513 24154-24154/? I/RxJava2MergeOperator: onNext:Hello Java
        //        2019-05-29 22:41:33.513 24154-24154/? I/RxJava2MergeOperator: onNext:Hello Kotlin
        //        2019-05-29 22:41:33.513 24154-24154/? I/RxJava2MergeOperator: onNext:Hello Scala
    }

    private void join() {
        Observable<Integer> o1 = Observable.just(1, 2, 3);
        Observable<Integer> o2 = Observable.just(4, 5, 6);

        o1.join(o2, integer -> Observable.just(String.valueOf(integer)).delay(200, TimeUnit.MILLISECONDS),
                integer -> Observable.just(String.valueOf(integer)).delay(200, TimeUnit.MILLISECONDS),
                (integer, integer2) -> integer + ":" + integer2)
                .subscribe(integer -> LogUtil.i(TAG, "onNext:" + integer));

        //        2019-05-29 22:35:29.773 23933-23933/? I/RxJava2MergeOperator: onNext:1:4
        //        2019-05-29 22:35:29.773 23933-23933/? I/RxJava2MergeOperator: onNext:2:4
        //        2019-05-29 22:35:29.773 23933-23933/? I/RxJava2MergeOperator: onNext:3:4
        //        2019-05-29 22:35:29.773 23933-23933/? I/RxJava2MergeOperator: onNext:1:5
        //        2019-05-29 22:35:29.773 23933-23933/? I/RxJava2MergeOperator: onNext:2:5
        //        2019-05-29 22:35:29.773 23933-23933/? I/RxJava2MergeOperator: onNext:3:5
        //        2019-05-29 22:35:29.774 23933-23933/? I/RxJava2MergeOperator: onNext:1:6
        //        2019-05-29 22:35:29.774 23933-23933/? I/RxJava2MergeOperator: onNext:2:6
        //        2019-05-29 22:35:29.774 23933-23933/? I/RxJava2MergeOperator: onNext:3:6
    }

    private void combineLatest() {
        Observable<Integer> odds = Observable.just(1, 3, 5);
        Observable<Integer> evens = Observable.just(2, 4, 6);

        Observable.combineLatest(odds, evens, (integer, integer2) -> integer + integer2)
                .subscribe(integer -> LogUtil.i(TAG, "onNext:" + integer),
                        throwable -> LogUtil.i(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete:"));

        //        2019-05-29 22:14:28.958 23176-23176/? I/RxJava2MergeOperator: onNext:7 (5+2)
        //        2019-05-29 22:14:28.958 23176-23176/? I/RxJava2MergeOperator: onNext:9 (5+4)
        //        2019-05-29 22:14:28.958 23176-23176/? I/RxJava2MergeOperator: onNext:11 (5+6)
        //        2019-05-29 22:14:28.958 23176-23176/? I/RxJava2MergeOperator: onComplete:
    }

    /**
     * 合并，多月的数字不会合并，按最少的合并
     */
    private void zip() {
        Observable<Integer> odds = Observable.just(1, 3, 5, 7, 9);
        Observable<Integer> evens = Observable.just(2, 4, 6);

        Observable.zip(odds, evens, (integer, integer2) -> integer + integer2)
                .subscribe(integer -> LogUtil.i(TAG, "onNext:" + integer),
                        throwable -> LogUtil.i(TAG, "onError:" + throwable.getMessage()),
                        () -> LogUtil.i(TAG, "onComplete:"));

        //        执行结果：
        //        2019-05-29 22:09:15.044 22882-22882/? I/RxJava2MergeOperator: onNext:3
        //        2019-05-29 22:09:15.044 22882-22882/? I/RxJava2MergeOperator: onNext:7
        //        2019-05-29 22:09:15.044 22882-22882/? I/RxJava2MergeOperator: onNext:11
        //        2019-05-29 22:09:15.044 22882-22882/? I/RxJava2MergeOperator: onComplete:
    }

    private void merge() {
        Observable<Integer> odds = Observable.just(1, 3, 5, 7, 9);
        Observable<Integer> evens = Observable.just(2, 4, 6);

        Observable.merge(odds, evens)
                .subscribe(integer -> LogUtil.i(TAG, "onNext:" + integer));

        /*
         * 执行结果：
         *
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:1
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:3
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:5
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:7
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:9
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:2
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:4
         * 2019-05-29 22:01:52.391 22490-22490/? I/RxJava2MergeOperator: onNext:6
         */
    }
}
