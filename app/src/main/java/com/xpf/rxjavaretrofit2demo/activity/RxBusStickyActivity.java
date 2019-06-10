package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.NormalEvent;
import com.xpf.rxjavaretrofit2demo.bean.StickyEvent;
import com.xpf.rxjavaretrofit2demo.eventbus.RxBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by x-sir on 2019-06-10 :)
 * Function:RxBus 粘性事件
 */
public class RxBusStickyActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus_sticky);
        initData();
        registerEvents();
    }

    private void initData() {
        RxBus.get().postSticky(new StickyEvent());
        RxBus.get().post(new NormalEvent());
    }

    private void registerEvents() {
        compositeDisposable.add(
                RxBus.get().
                        registerSticky(
                                StickyEvent.class,
                                AndroidSchedulers.mainThread(),
                                event -> Toast.makeText(RxBusStickyActivity.this, "this is StickyEvent", Toast.LENGTH_SHORT).show())
        );

        compositeDisposable.add(
                RxBus.get()
                        .register(
                                NormalEvent.class,
                                AndroidSchedulers.mainThread(),
                                event -> Toast.makeText(RxBusStickyActivity.this, "this is NormalEvent", Toast.LENGTH_SHORT).show())
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().removeStickyEvent(StickyEvent.class);
        compositeDisposable.clear();
    }
}
