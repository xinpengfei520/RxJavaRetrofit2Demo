package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 不同场景下的操作符练习
 */
public class EstablishOperatorActivity extends AppCompatActivity {

    private static final String TAG = "EstablishOperator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establish_operator);

        //repeatWhen();
        //fromArray();
        //fromIterable();
        //interval();
        //timer();
        observableSubscribeObserver();
    }

    private void timer() {
        // 定时操作，该例子 = 延迟2s后，进行日志输出操作
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "在2s后进行了该操作");
                    }

                });

        // 注：timer操作符默认运行在一个新线程上
        // 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
    }

    private void interval() {
        // 周期性操作，该例子发送的事件序列特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个） = 每隔1s进行1次操作
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        LogUtil.d(TAG, "每隔1s进行1次操作");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "对Complete事件作出响应");
                    }
                });

        // 注：interval默认在computation调度器上执行
        // 也可自定义指定线程调度器（第3个参数）：interval(long,TimeUnit,Scheduler)
    }

    private void fromIterable() {
        // 1. 集合遍历，设置一个集合
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 2. 通过fromIterable()将集合中的对象 / 数据发送出去
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "集合遍历");
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogUtil.d(TAG, "集合中的数据元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "遍历结束");
                    }
                });
    }

    private void fromArray() {
        // 1. 数组遍历，设置需要传入的数组
        Integer[] items = {0, 1, 2, 3, 4};

        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "数组遍历");
                    }

                    @Override
                    public void onNext(Integer value) {
                        LogUtil.d(TAG, "数组中的元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "遍历结束");
                    }

                });
    }

    private void repeatWhen() {
        Observable.just(1, 2, 4).repeatWhen(objectObservable -> {
            // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
            // 以此决定是否重新订阅 & 发送原来的 Observable
            // 此处有2种情况：
            // 1. 若新被观察者（Observable）返回1个Complete事件，则不重新订阅 & 发送原来的 Observable
            // 2. 若新被观察者（Observable）返回1个Next事件，则重新订阅 & 发送原来的 Observable
            return objectObservable.flatMap(throwable -> {

                // 情况1：若新被观察者（Observable）返回1个Complete事件，则不重新订阅 & 发送原来的 Observable
                return Observable.empty();
                // 返回Error
                //return Observable.error(new Throwable("不再重新订阅事件"));

                // 情况2：若新被观察者（Observable）返回1个Next事件，则重新订阅 & 发送原来的 Observable
                // return Observable.just(1);
            });
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                LogUtil.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {

                LogUtil.d(TAG, "对Error事件作出响应：" + e.toString());
            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "对Complete事件作出响应");
            }
        });


        // 具体使用
        Observable.just(1, 2, 4).repeatWhen(objectObservable -> {
            return Observable.just(1);
            //return objectObservable;
            //return Observable.empty().delay(3, TimeUnit.SECONDS);
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                LogUtil.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void observableSubscribeObserver() {
        // 完整创建被观察者，步骤1：通过create（）创建完整的被观察者对象
        // 2. 在复写的subscribe（）里定义需要发送的事件
        Observable<Integer> observable = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        });

        // 步骤2：创建观察者 Observer 并 定义响应事件行为
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                LogUtil.d(TAG, "接收到了事件 = " + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtil.d(TAG, "对Complete事件作出响应");
            }
        };

        // 步骤3：通过订阅（subscribe）连接观察者和被观察者
        observable.subscribe(observer);
    }
}
