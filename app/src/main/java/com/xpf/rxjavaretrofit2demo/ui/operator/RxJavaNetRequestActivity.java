package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.Translation;
import com.xpf.rxjavaretrofit2demo.service.GetRequestService;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_net_request);

        requestByConditionLoop();
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

                    /*
                     * 步骤3：通过Retrofit发送网络请求
                     **/
                    // a. 创建Retrofit对象
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://fy.iciba.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                    // b. 创建 网络请求接口 的实例
                    GetRequestService request = retrofit.create(GetRequestService.class);

                    // c. 采用Observable<...>形式 对 网络请求 进行封装
                    Observable<Translation> observable = request.getCall();
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
