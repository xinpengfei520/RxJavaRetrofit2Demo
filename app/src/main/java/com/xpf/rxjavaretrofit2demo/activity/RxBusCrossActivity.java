package com.xpf.rxjavaretrofit2demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.safframework.log.L;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.CrossActivityEvent;
import com.xpf.rxjavaretrofit2demo.bean.ExceptionEvent;
import com.xpf.rxjavaretrofit2demo.eventbus.RxBus;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by x-sir on 2019-06-10 :)
 * Function:RxBus Activity 之间发送事件
 */
public class RxBusCrossActivity extends AppCompatActivity {

    @BindView(R.id.btnOther)
    Button btnOther;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "RxBusCrossActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus_cross);
        ButterKnife.bind(this);
        RxView.clicks(btnOther)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> startActivity(new Intent(RxBusCrossActivity.this, OtherPageActivity.class)));

        registerEvents();
    }

    private void registerEvents() {
        compositeDisposable.add(
                RxBus.get()
                        .register(CrossActivityEvent.class,
                                AndroidSchedulers.mainThread(),
                                event -> Toast.makeText(RxBusCrossActivity.this, "来自 RxBusCrossActivity 的 Toast", Toast.LENGTH_SHORT).show())
        );

        compositeDisposable.add(
                RxBus.get()
                        .register(ExceptionEvent.class,
                                AndroidSchedulers.mainThread(),
                                event -> LogUtil.e(TAG, "accept()"),
                                (Consumer<Throwable>) throwable -> L.i(throwable.getMessage()))
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
