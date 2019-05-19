package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class RxJavaOperatorActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaOperatorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator);
        ButterKnife.bind(this);

        // 1.实例化一个 Button
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //createObservable();
        //iterable();
        fromFuture();
    }

    private void fromFuture() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(new MyCallable());

        Observable.fromFuture(future, 2, TimeUnit.SECONDS)
                .subscribe(s -> LogUtil.i(TAG, "任务运行的结果：" + s)
                        , throwable -> LogUtil.e(TAG, "onError:" + throwable.getMessage()));
    }

    private void iterable() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i);
        }

        Observable.just(list).subscribe((Consumer<List>) list1 -> LogUtil.i(TAG, "just():" + Arrays.toString(list1.toArray())));

        Observable.fromIterable(list).subscribe(integer -> LogUtil.i(TAG, "fromIterable():" + integer));

        Observable.just(1, 2, 3, 4, 5).subscribe(integer -> LogUtil.i(TAG, "just():" + integer));
    }

    private void createObservable() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            try {
                if (!emitter.isDisposed()) {
                    for (int i = 0; i < 10; i++) {
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribe(integer -> LogUtil.i(TAG, "onNext():" + integer),
                throwable -> LogUtil.i(TAG, "onError():" + throwable.getMessage()),
                () -> LogUtil.i(TAG, "onComplete()"));
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            LogUtil.i(TAG, "call():在这里模拟一些耗时操作...");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
