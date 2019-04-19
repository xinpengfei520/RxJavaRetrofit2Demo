package com.xpf.rxjavaretrofit2demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xpf.rxjavaretrofit2demo.ui.movie.view.MovieFlowableActivity;
import com.xpf.rxjavaretrofit2demo.activity.HttpUrlConnectionActivity;
import com.xpf.rxjavaretrofit2demo.activity.ObservableActivity;
import com.xpf.rxjavaretrofit2demo.activity.Okhttp3DemoActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaDemoActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaGitHubActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaHelloWorldActivity;
import com.xpf.rxjavaretrofit2demo.activity.SimpleRetrofit;
import com.xpf.rxjavaretrofit2demo.activity.VolleyDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by x-sir on 2016-12-21 :)
 * Function:Retrofit2 + RxJava Demo
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final String GITHUB_ACCOUNT = "xinpengfei520";
    @BindView(R.id.get0)
    Button get0;
    @BindView(R.id.tvHelloWorld)
    Button tvHelloWorld;
    @BindView(R.id.btnObservable)
    Button btnObservable;
    @BindView(R.id.btnFlowable)
    Button btnFlowable;
    @BindView(R.id.btnRxJavaGitHub)
    Button btnRxJavaGitHub;
    @BindView(R.id.get5)
    Button get5;
    @BindView(R.id.get6)
    Button get6;
    @BindView(R.id.get7)
    Button get7;
    @BindView(R.id.get8)
    Button get8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get0, R.id.get5, R.id.get6, R.id.get7, R.id.get8, R.id.tvHelloWorld,
            R.id.btnObservable, R.id.btnFlowable, R.id.btnRxJavaGitHub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get0:
                Intent intent = new Intent(MainActivity.this, SimpleRetrofit.class);
                intent.putExtra("account", GITHUB_ACCOUNT);
                startActivity(intent);
                break;
            case R.id.get5:
                jumpToActivity(HttpUrlConnectionActivity.class);
                break;
            case R.id.get6:
                jumpToActivity(VolleyDemoActivity.class);
                break;
            case R.id.get7:
                jumpToActivity(Okhttp3DemoActivity.class);
                break;
            case R.id.get8:
                jumpToActivity(RxJavaDemoActivity.class);
                break;
            case R.id.tvHelloWorld:
                jumpToActivity(RxJavaHelloWorldActivity.class);
                break;
            case R.id.btnObservable:
                jumpToActivity(ObservableActivity.class);
                break;
            case R.id.btnFlowable:
                jumpToActivity(MovieFlowableActivity.class);
                break;
            case R.id.btnRxJavaGitHub:
                jumpToActivity(RxJavaGitHubActivity.class);
                break;
            default:
                break;
        }
    }

    private void jumpToActivity(Class<?> clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }

}
