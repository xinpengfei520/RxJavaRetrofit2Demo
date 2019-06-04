package com.xpf.rxjavaretrofit2demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xpf.rxjavaretrofit2demo.activity.RxJavaParallelismActivity;
import com.xpf.rxjavaretrofit2demo.ui.movie.view.MovieFlowableActivity;
import com.xpf.rxjavaretrofit2demo.activity.HttpUrlConnectionActivity;
import com.xpf.rxjavaretrofit2demo.activity.ObservableActivity;
import com.xpf.rxjavaretrofit2demo.activity.Okhttp3DemoActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaDemoActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaGitHubActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaHelloWorldActivity;
import com.xpf.rxjavaretrofit2demo.activity.SimpleRetrofit;
import com.xpf.rxjavaretrofit2demo.activity.VolleyDemoActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.CombineOperatorActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.EstablishOperatorActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJava2ConditionOperatorActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJava2MergeOperator;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJavaBackPressure;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJavaDisposableActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJavaNetRequestActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJavaOperatorActivity;
import com.xpf.rxjavaretrofit2demo.ui.operator.RxJavaTransformerActivity;

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
    @BindView(R.id.get9)
    Button get9;
    @BindView(R.id.get10)
    Button get10;
    @BindView(R.id.get11)
    Button get11;
    @BindView(R.id.get12)
    Button get12;
    @BindView(R.id.get13)
    Button get13;
    @BindView(R.id.get14)
    Button get14;
    @BindView(R.id.get15)
    Button get15;
    @BindView(R.id.get16)
    Button get16;
    @BindView(R.id.get17)
    Button get17;
    @BindView(R.id.get18)
    Button get18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get0, R.id.get5, R.id.get6, R.id.get7, R.id.get8, R.id.get9, R.id.get10, R.id.get11,
            R.id.get12, R.id.get13, R.id.get14, R.id.get15, R.id.get16, R.id.get17, R.id.get18, R.id.tvHelloWorld, R.id.btnObservable, R.id.btnFlowable, R.id.btnRxJavaGitHub})
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
            case R.id.get9:
                jumpToActivity(RxJavaOperatorActivity.class);
                break;
            case R.id.get10:
                jumpToActivity(RxJava2ConditionOperatorActivity.class);
                break;
            case R.id.get11:
                jumpToActivity(CombineOperatorActivity.class);
                break;
            case R.id.get12:
                jumpToActivity(EstablishOperatorActivity.class);
                break;
            case R.id.get13:
                jumpToActivity(RxJavaNetRequestActivity.class);
                break;
            case R.id.get14:
                jumpToActivity(RxJava2MergeOperator.class);
                break;
            case R.id.get15:
                jumpToActivity(RxJavaBackPressure.class);
                break;
            case R.id.get16:
                jumpToActivity(RxJavaDisposableActivity.class);
                break;
            case R.id.get17:
                jumpToActivity(RxJavaTransformerActivity.class);
                break;
            case R.id.get18:
                jumpToActivity(RxJavaParallelismActivity.class);
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
