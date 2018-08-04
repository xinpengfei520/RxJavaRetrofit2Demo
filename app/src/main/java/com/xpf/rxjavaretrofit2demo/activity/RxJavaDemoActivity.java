package com.xpf.rxjavaretrofit2demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.xpf.rxjavaretrofit2demo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by x-sir on 2016-12-21 :)
 * Function:RxJavaDemoActivity
 */
public class RxJavaDemoActivity extends Activity {

    private static final String TAG = "RxJavaDemoActivity";
    // 被观察者,事件源
    private Observable<String> observable;
    // 只发出一个事件就结束的Observable
    private Observable<String> oneActionObservable;
    // 观察者
    private Subscriber<String> subscriber;
    @BindView(R.id.tv)
    TextView tv;
    private List<Integer> nums;
    private List<Integer> numbers;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_demo);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("item_" + i);
        }

        nums = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            nums.add(i * (i + 10));
        }

        numbers = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            numbers.add(i);
        }

        try {
            InputStream is = getAssets().open("code.txt");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            tv.setText(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }

        InitObserver();
        InitSubscriber();

        /**
         * 假设将被观察者事件源发生的事件 'hello world' 在观察者Subscribe中实现时,需要发生一些变化。
         * 操作符就是为了解决对Observable对象的变换的问题
         * 操作符用于在Observable和最终的Subscriber之间修改Observable发出的事件
         * RxJava提供了很多很有用的操作符
         */
        Observable
                .range(12, 10)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Toast.makeText(RxJavaDemoActivity.this, "integer===" + integer, Toast.LENGTH_SHORT).show();
                    }
                });

        Observable
                .just(System.currentTimeMillis())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.e(RxJavaDemoActivity.class.getSimpleName(), "aLong===" + aLong + aLong);
                    }
                });

        Observable
                .from(numbers)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer != null;
                    }
                })
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return 10 % integer;
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(RxJavaDemoActivity.class.getSimpleName(), "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(RxJavaDemoActivity.class.getSimpleName(), "onError---->" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(RxJavaDemoActivity.class.getSimpleName(), "onNext---- >Remainder is " + integer);
                    }
                });
        BackPressureTest();
    }

    // 背压测试
    private void BackPressureTest() {
        Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError===" + e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext===" + aLong);
                    }
                });
    }

    // 创建观察者Subscribe
    private void InitSubscriber() {
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(RxJavaDemoActivity.class.getSimpleName(), "onNext--->" + s);
                Toast.makeText(RxJavaDemoActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
    }

    // 创建被观察者Observable
    private void InitObserver() {
        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world!");
                subscriber.onCompleted();
            }
        });
        // 只发出一个事件就结束的Observable
        oneActionObservable = Observable.just("hello world!");
    }
}
