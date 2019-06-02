package com.xpf.rxjavaretrofit2demo.ui.operator;

import android.os.Bundle;

import com.trello.rxlifecycle3.android.RxLifecycleAndroid;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.xpf.rxjavaretrofit2demo.R;

import io.reactivex.Observable;

/**
 * Created by x-sir on 2019-06-02 :)
 * Function:RxJava 2.x Disposable
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class RxJavaDisposableActivity extends RxAppCompatActivity {

    private Observable<Integer> myObservable = Observable.range(0, 25);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_disposable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myObservable
                .compose(RxLifecycleAndroid.bindActivity(lifecycle()))
                //.compose(RxLifecycle.bindUntilEvent(lifecycle(), ActivityEvent.DESTROY))
                .subscribe();
    }
}
