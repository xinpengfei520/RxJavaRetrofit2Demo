package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.Fragment1Event;
import com.xpf.rxjavaretrofit2demo.bean.Fragment2Event;
import com.xpf.rxjavaretrofit2demo.eventbus.RxBus;
import com.xpf.rxjavaretrofit2demo.ui.fragment.Fragment1;
import com.xpf.rxjavaretrofit2demo.ui.fragment.Fragment2;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by x-sir on 2019-06-08 :)
 * Function:RxBusActivity
 */
public class RxBusActivity extends AppCompatActivity {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        initViews();
        registerEvents();
    }

    private void initViews() {
        fragment1 = Fragment1.newInstance();
        fragment2 = Fragment2.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment1, fragment1, fragment1.getClass().getName());
        transaction.replace(R.id.fragment2, fragment2, fragment2.getClass().getName());
        transaction.commit();
    }

    private void registerEvents() {
        compositeDisposable.add(RxBus.get().toObservable(Fragment1Event.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<Object>) o -> fragment1.getTextView1().setText("Fragment1 已经接收到事件")));

        compositeDisposable.add(RxBus.get().toObservable(Fragment2Event.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<Object>) o -> fragment2.getTextView2().setText("Fragment2 已经接收到事件")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
