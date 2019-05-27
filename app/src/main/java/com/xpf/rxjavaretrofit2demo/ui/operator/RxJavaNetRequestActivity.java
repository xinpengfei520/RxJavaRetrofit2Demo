package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.Translation;
import com.xpf.rxjavaretrofit2demo.service.GetRequestService;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by x-sir on 2019-05-27 :)
 * Function:RxJava + Retrofit 2.x 网络请求示例
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaNetRequestActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaNetRequest";
    /**
     * 模拟轮询服务器次数
     */
    private int mLoopTimes = 0;
    /**
     * 最大可重试次数
     */
    private int maxConnectCount = 10;
    /**
     * 当前已重试次数
     */
    private int currentRetryCount = 0;
    /**
     * 重试等待时间
     */
    private int waitRetryTime = 0;
    private GetRequestService mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_net_request);

        initData();
        //requestByConditionLoop();
        //requestByTimes();
        requestByTryWhen();
    }

    private void requestByTryWhen() {
        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = mRequest.getCall();

        // 步骤4：发送网络请求 & 通过retryWhen（）进行重试
        // 注：主要异常才会回调retryWhen（）进行重试
        observable
                .retryWhen(throwableObservable -> {
                    // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                    return throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                        // 输出异常信息
                        LogUtil.d(TAG, "throwable:" + throwable.getMessage());

                        // 需求1：根据异常类型选择是否重试即，当发生的异常 = 网络异常 = IO异常 才选择重试
                        if (throwable instanceof IOException) {
                            LogUtil.d(TAG, "属于IO异常，需重试");

                            // 需求2：限制重试次数，即，当已重试次数 < 设置的重试次数，才选择重试
                            if (currentRetryCount < maxConnectCount) {
                                // 记录重试次数
                                currentRetryCount++;
                                LogUtil.d(TAG, "重试次数 = " + currentRetryCount);

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                waitRetryTime = 1000 + currentRetryCount * 1000;
                                LogUtil.d(TAG, "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);

                            } else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));
                            }
                            // 若发生的异常不属于I/O异常，则不重试
                            // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                        } else {
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation result) {
                        // 接收服务器返回的数据
                        LogUtil.d(TAG, "发送成功");
                        result.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取停止重试的信息
                        LogUtil.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mRequest = retrofit.create(GetRequestService.class);
    }

    private void requestByTimes() {
        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = mRequest.getCall();

        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
        observable.repeatWhen(objectObservable -> {
            // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
            // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
            // 此处有2种情况：
            // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
            // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
            return objectObservable.flatMap((Function<Object, ObservableSource<?>>) throwable -> {

                // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                if (mLoopTimes > 3) {
                    // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                    return Observable.error(new Throwable("轮询结束"));
                }
                // 若轮询次数＜4次，则发送1Next事件以继续轮询
                // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
            });

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Translation result) {
                        // e.接收服务器返回的数据
                        result.show();
                        mLoopTimes++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        LogUtil.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 条件轮训请求
     */
    private void requestByConditionLoop() {
        // 步骤1：采用interval（）延迟发送
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext(integer -> {
                    LogUtil.d(TAG, "第 " + integer + " 次轮询");
                    // c. 采用Observable<...>形式 对 网络请求 进行封装
                    Observable<Translation> observable = mRequest.getCall();
                    // d. 通过线程切换发送网络请求
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Translation>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(Translation result) {
                                    // e.接收服务器返回的数据
                                    result.show();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtil.d(TAG, "请求失败");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "onError():" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "onComplete()");
                    }
                });
    }
}
