package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * RxJava 版本的 HelloWorld 实现
 */
public class RxJavaHelloWorldActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaHelloWorldActivity";
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_hello_world);
        ButterKnife.bind(this);

        singleParameterSubscribe();

        fourParametersSubscribe();

        doOperatorExercise();
    }

    /**
     * 单参数
     */
    private void singleParameterSubscribe() {
        // 1.使用 create
        Observable.create((ObservableOnSubscribe<String>) e -> e.onNext("Hello World!")).subscribe(s -> LogUtil.iLogging(TAG, s));
    }

    /**
     * 4个参数的
     */
    private void fourParametersSubscribe() {
        // 2.使用 just，subscribe 公有 4 个重载方法，这里使用的是 4个参数的重载，上面使用的是一个参数的重载
        Observable.just("Hello World!").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.iLogging(TAG, s);
                textView.setText(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtil.eLogging(TAG, throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                LogUtil.iLogging(TAG, "onComplete()");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                LogUtil.iLogging(TAG, "subscribe");
            }
        });
    }

    private void doOperatorExercise() {
        Observable.just("hello")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "doOnNext() -> " + s);
                    }
                })
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "doAfterNext() -> " + s);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.i(TAG, "doOnComplete()");
                    }
                })
                // 订阅之后的回调
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LogUtil.i(TAG, "doOnSubscribe()");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.i(TAG, "doAfterTerminate()");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.i(TAG, "doFinally()");
                    }
                })
                // Observable 每发射一个数据就会触发这个回调，包含 onNext、onError、onCompleted
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        LogUtil.i(TAG, "doOnEach():" + (stringNotification.isOnNext() ?
                                "onNext" : stringNotification.isOnComplete() ? "onCompleted" : "onError"));
                    }
                })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LogUtil.i(TAG, "doOnLifecycle():" + disposable.isDisposed());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.i(TAG, "doOnLifecycle() -> run");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "收到消息：" + s);
                    }
                });
    }
}
