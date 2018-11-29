package com.xpf.rxjavaretrofit2demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xpf.rxjavaretrofit2demo.activity.HttpUrlConnectionActivity;
import com.xpf.rxjavaretrofit2demo.activity.Okhttp3DemoActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaDemoActivity;
import com.xpf.rxjavaretrofit2demo.activity.RxJavaHelloWorldActivity;
import com.xpf.rxjavaretrofit2demo.activity.SimpleRetrofit;
import com.xpf.rxjavaretrofit2demo.activity.VolleyDemoActivity;
import com.xpf.rxjavaretrofit2demo.api.GithubService;
import com.xpf.rxjavaretrofit2demo.api.RequestUrl;
import com.xpf.rxjavaretrofit2demo.bean.GithubUserBean;
import com.xpf.rxjavaretrofit2demo.bean.UserFollowerBean;
import com.xpf.rxjavaretrofit2demo.utils.GenServiceUtil;
import com.xpf.rxjavaretrofit2demo.utils.LogUtil;
import com.xpf.rxjavaretrofit2demo.utils.ToastUtil;
import com.xpf.rxjavaretrofit2demo.view.XDialog;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by x-sir on 2016-12-21 :)
 * Function:Retrofit2 + RxJava Demo
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Context mContext;
    private String name;
    private ProgressDialog loading;

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.get0)
    Button get0;
    @BindView(R.id.tvHelloWorld)
    Button tvHelloWorld;
    @BindView(R.id.get1)
    Button get1;
    @BindView(R.id.get2)
    Button get2;
    @BindView(R.id.get3)
    Button get3;
    @BindView(R.id.get4)
    Button get4;
    @BindView(R.id.get5)
    Button get5;
    @BindView(R.id.get6)
    Button get6;
    @BindView(R.id.get7)
    Button get7;
    @BindView(R.id.get8)
    Button get8;
    @BindView(R.id.viewShell)
    LinearLayout viewShell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this; // 接受上下文要放在第一行
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loading = XDialog.create(this);
    }

    @OnClick({R.id.get0, R.id.get1, R.id.get2, R.id.get3, R.id.get4, R.id.get5, R.id.get6, R.id.get7,
            R.id.get8, R.id.tvHelloWorld})
    public void onClick(View view) {
        name = etUserName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort("Input is empty!");
            return;
        }
        viewShell.removeAllViews();
        loading.show();

        switch (view.getId()) {
            case R.id.get0:
                Intent intent = new Intent(MainActivity.this, SimpleRetrofit.class);
                intent.putExtra("account", name);
                startActivity(intent);
                break;
            case R.id.get1:
                LazyRetrofit();
                break;
            case R.id.get2:
                EasyRetrofit();
                break;
            case R.id.get3:
                RxRetrofit();
                break;
            case R.id.get4:
                RxRetrofitList();
                break;
            case R.id.get5:
                loading.dismiss();
                jumpToActivity(HttpUrlConnectionActivity.class);
                break;
            case R.id.get6:
                loading.dismiss();
                jumpToActivity(VolleyDemoActivity.class);
                break;
            case R.id.get7:
                loading.dismiss();
                jumpToActivity(Okhttp3DemoActivity.class);
                break;
            case R.id.get8:
                loading.dismiss();
                jumpToActivity(RxJavaDemoActivity.class);
                break;
            case R.id.tvHelloWorld:
                jumpToActivity(RxJavaHelloWorldActivity.class);
                break;
        }
    }

    private void jumpToActivity(Class<?> clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }

    private void RxRetrofitList() {
        GithubService service = GenServiceUtil.createService(GithubService.class);
        Observable<List<UserFollowerBean>> observable = service.followers(name);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userFollowerBeans -> {
                    // 遍历转大写
                    for (UserFollowerBean bean : userFollowerBeans) {
                        String name = bean.getLogin().substring(0, 1).toUpperCase() +
                                bean.getLogin().substring(1, bean.getLogin().length());
                        bean.setLogin(name);
                    }
                    return userFollowerBeans;
                })
                .map(userFollowerBeans -> {
                    // 排序
                    Collections.sort(userFollowerBeans, (o1, o2) -> o1.getLogin().compareTo(o2.getLogin()));
                    return userFollowerBeans;
                })
                .subscribe(new Observer<List<UserFollowerBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.iLogging(TAG, "onSubscribe()");
                    }

                    @Override
                    public void onNext(List<UserFollowerBean> userFollowerBeans) {
                        LogUtil.iLogging(TAG, "onNext()");
                        setFollowersView(userFollowerBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.eLogging(TAG, "onError()");
                        loading.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.iLogging(TAG, "onComplete()");
                        loading.dismiss();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setFollowersView(List<UserFollowerBean> followers) {
        if (followers != null && followers.size() > 0) {
            for (UserFollowerBean user : followers) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
                TextView title = view.findViewById(R.id.title);
                TextView id = view.findViewById(R.id.userId);
                TextView createTime = view.findViewById(R.id.createTime);
                TextView updateTime = view.findViewById(R.id.updateTime);
                ImageView avatar = view.findViewById(R.id.avatar);

                title.setText("Name: " + user.getLogin());
                id.setText("Id: " + String.valueOf(user.getId()));
                createTime.setText("");
                updateTime.setText("");
                Glide.with(mContext).load(user.getAvatar_url()).into(avatar);
                viewShell.addView(view);
            }
        } else {
            ToastUtil.showShort("result is null");
        }
    }

    /**
     * RxJava + Retrofit
     */
    private void RxRetrofit() {
        GithubService service = GenServiceUtil.createService(GithubService.class);
        final Call<GithubUserBean> call = service.getUser(name);
        // create a observable object
        Observable<GithubUserBean> observable =
                Observable.create(emitter -> {
                    Response<GithubUserBean> bean = null;
                    try {
                        bean = call.execute();
                        emitter.onNext(bean.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        emitter.onError(e);
                    }
                    emitter.onComplete();
                });

        // subscribe a event
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(githubUserBean -> {
                    if (TextUtils.isEmpty(githubUserBean.getBio())) {
                        githubUserBean.setBio("empty message!");
                    }
                    return githubUserBean;
                })
                .subscribe(new Observer<GithubUserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.iLogging(TAG, "onSubscribe()");
                    }

                    @Override
                    public void onNext(GithubUserBean githubUserBean) {
                        LogUtil.iLogging(TAG, "onNext()");
                        setUserView(githubUserBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.eLogging(TAG, "onError()");
                        loading.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.iLogging(TAG, "onComplete()");
                        loading.dismiss();
                    }
                });
    }

    /**
     * 使用 GenServiceUtil 进行简单封装
     */
    private void EasyRetrofit() {
        GithubService service = GenServiceUtil.createService(GithubService.class);
        Call<GithubUserBean> call = service.getUser(name);
        call.enqueue(new Callback<GithubUserBean>() {
            @Override
            public void onResponse(Call<GithubUserBean> call, Response<GithubUserBean> response) {
                loading.dismiss();
                GithubUserBean bean = response.body();
                setUserView(bean);
            }

            @Override
            public void onFailure(Call<GithubUserBean> call, Throwable t) {
                loading.dismiss();
            }
        });

    }

    private void LazyRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit
                        .Builder()
                        .baseUrl(RequestUrl.BASE_URL)
                        // add converter bean factory
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        GithubService service = retrofit.create(GithubService.class);
        Call<GithubUserBean> call = service.getUser(name);

        call.enqueue(new Callback<GithubUserBean>() {
            @Override
            public void onResponse(Call<GithubUserBean> call, Response<GithubUserBean> response) {
                loading.dismiss();
                GithubUserBean bean = response.body();
                setUserView(bean);
            }

            @Override
            public void onFailure(Call<GithubUserBean> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setUserView(GithubUserBean userBean) {
        if (userBean != null) {
            viewShell.removeAllViews();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
            TextView title = view.findViewById(R.id.title);
            TextView id = view.findViewById(R.id.userId);
            TextView createTime = view.findViewById(R.id.createTime);
            TextView updateTime = view.findViewById(R.id.updateTime);
            TextView bio = view.findViewById(R.id.bio);
            ImageView avatar = view.findViewById(R.id.avatar);

            title.setText("Name: " + userBean.getLogin());
            bio.setText("Bio: " + userBean.getBio());
            id.setText("Id: " + String.valueOf(userBean.getId()));
            createTime.setText("createTime: " + userBean.getCreated_at());
            updateTime.setText("updateTime: " + userBean.getUpdated_at());
            Glide.with(mContext).load(userBean.getAvatar_url()).into(avatar);
            viewShell.addView(view);
        } else {
            Toast.makeText(mContext, "result is null", Toast.LENGTH_SHORT).show();
        }
    }
}
