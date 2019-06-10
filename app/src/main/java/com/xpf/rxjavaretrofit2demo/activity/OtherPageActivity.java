package com.xpf.rxjavaretrofit2demo.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.CrossActivityEvent;
import com.xpf.rxjavaretrofit2demo.bean.ExceptionEvent;
import com.xpf.rxjavaretrofit2demo.eventbus.RxBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by x-sir on 2019-06-10 :)
 * Function:
 */
public class OtherPageActivity extends AppCompatActivity {

    @BindView(R.id.btnSend)
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_page);
        ButterKnife.bind(this);
        RxBus.get().post(new CrossActivityEvent());

        RxView.clicks(btnSend)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> RxBus.get().post(new ExceptionEvent()));
    }
}
