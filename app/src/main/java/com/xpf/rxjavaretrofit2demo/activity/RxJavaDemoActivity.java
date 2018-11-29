package com.xpf.rxjavaretrofit2demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;
import com.xpf.rxjavaretrofit2demo.utils.ToastUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by x-sir on 2016-12-21 :)
 * Function:RxJavaDemoActivity
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaDemoActivity extends Activity {

    private static final String TAG = "RxJavaDemoActivity";
    @BindView(R.id.tv)
    TextView tv;

    // 被观察者,事件源
    private Observable<String> observable;
    // 只发出一个事件就结束的Observable
    private Observable<String> oneActionObservable;
    // 观察者
    private Subscriber<String> subscriber;

    private List<Integer> mNumbers;
    private List<Integer> numberList;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_demo);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add("item_" + i);
        }

        mNumbers = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mNumbers.add(i * (i + 10));
        }

        numberList = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            numberList.add(i);
        }

        InitObserver();
        InitSubscriber();

        /*
         * 假设将被观察者事件源发生的事件 'hello world' 在观察者Subscribe中实现时,需要发生一些变化。
         * 操作符就是为了解决对Observable对象的变换的问题
         * 操作符用于在Observable和最终的Subscriber之间修改Observable发出的事件
         * RxJava提供了很多很有用的操作符
         */
        Observable
                .range(12, 10)
                .subscribe(integer -> ToastUtil.showShort("integer===" + integer));

        Observable
                .just(System.currentTimeMillis())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "aLong===" + aLong);
                    }
                });

        Observable
                .fromArray(numberList)
                .filter(new Predicate<List<Integer>>() {
                    @Override
                    public boolean test(List<Integer> integers) throws Exception {
                        return integers != null;
                    }
                })
                .map(new Function<List<Integer>, List<Integer>>() {
                    @Override
                    public List<Integer> apply(List<Integer> integers) throws Exception {
                        List<Integer> list = new ArrayList<>();
                        for (Integer integer : integers) {
                            integer = 10 % integer;
                            list.add(integer);
                        }
                        return list;
                    }
                })
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.iLogging(TAG, "onSubscribe()");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        LogUtil.iLogging(TAG, "onNext()");
                        LogUtil.collection(TAG, integers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.eLogging(TAG, "onError()");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.iLogging(TAG, "onComplete()");
                    }
                });
        BackPressureTest();
    }

    /**
     * 背压测试
     */
    private void BackPressureTest() {
        Observable
                .interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.iLogging(TAG, "onSubscribe()");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.iLogging(TAG, "onNext()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.eLogging(TAG, "onError()");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.iLogging(TAG, "onComplete()");
                    }
                });
    }

    /**
     * 创建观察者 Subscribe
     */
    private void InitSubscriber() {
        subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                LogUtil.iLogging(TAG, "onSubscribe()");
            }

            @Override
            public void onComplete() {
                LogUtil.iLogging(TAG, "onComplete()");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.eLogging(TAG, "onError()");
            }

            @Override
            public void onNext(String s) {
                LogUtil.iLogging(TAG, "onNext()");
                ToastUtil.showShort(s);
            }
        };
    }

    /**
     * 创建被观察者Observable
     */
    private void InitObserver() {
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("hello world!");
                e.onComplete();
            }
        });
        // 只发出一个事件就结束的Observable
        oneActionObservable = Observable.just("hello world!");
    }
}
