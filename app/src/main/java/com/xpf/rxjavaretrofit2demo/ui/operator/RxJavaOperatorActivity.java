package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
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

        helloWorld();
    }

    private void helloWorld() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i);
        }

        Observable.just(list)
                .subscribe(new Consumer<List>() {
                    @Override
                    public void accept(List list) throws Exception {
                        LogUtil.i(TAG, "just():" + Arrays.toString(list.toArray()));
                    }
                });

        Observable.fromIterable(list)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "fromIterable():" + integer);
                    }
                });
    }

    private void createObservable() {
//        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
//            try {
//                if (!emitter.isDisposed()) {
//                    for (int i = 0; i < 10; i++) {
//                        emitter.onNext(i);
//                    }
//                    emitter.onComplete();
//                }
//            } catch (Exception e) {
//                emitter.onError(e);
//            }
//        }).subscribe(integer -> LogUtil.i(TAG, "onNext():" + integer),
//                throwable -> LogUtil.i(TAG, "onError():" + throwable.getMessage()),
//                () -> LogUtil.i(TAG, "onComplete()"));
    }
}
