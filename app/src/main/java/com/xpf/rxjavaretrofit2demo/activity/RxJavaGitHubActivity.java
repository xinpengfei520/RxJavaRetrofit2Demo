package com.xpf.rxjavaretrofit2demo.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xpf.rxjavaretrofit2demo.R;
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
 * RxJava 版本 GitHub 接口练习
 */
public class RxJavaGitHubActivity extends AppCompatActivity {

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.btnLazyRetrofit)
    Button btnLazyRetrofit;
    @BindView(R.id.btnEasyRetrofit)
    Button btnEasyRetrofit;
    @BindView(R.id.btnRxRetrofit)
    Button btnRxRetrofit;
    @BindView(R.id.btnRxRetrofitList)
    Button btnRxRetrofitList;
    private ProgressDialog loading;
    private static final String GITHUB_ACCOUNT = "xinpengfei520";
    private static final String TAG = "RxJavaGitHubActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_git_hub);
        ButterKnife.bind(this);
        loading = XDialog.create(this);
    }

    @OnClick({R.id.btnLazyRetrofit, R.id.btnEasyRetrofit, R.id.btnRxRetrofit, R.id.btnRxRetrofitList})
    public void onViewClicked(View view) {
        loading.show();
        switch (view.getId()) {
            case R.id.btnLazyRetrofit:
                lazyRetrofit2();
                break;
            case R.id.btnEasyRetrofit:
                easyRetrofit2();
                break;
            case R.id.btnRxRetrofit:
                rxRetrofit2();
                break;
            case R.id.btnRxRetrofitList:
                rxRetrofit2List();
                break;
        }
    }

    private void rxRetrofit2List() {
        GithubService service = GenServiceUtil.createService(GithubService.class);
        Observable<List<UserFollowerBean>> observable = service.followers(GITHUB_ACCOUNT);

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

    /**
     * RxJava + Retrofit
     */
    private void rxRetrofit2() {
        GithubService service = GenServiceUtil.createService(GithubService.class);
        final Call<GithubUserBean> call = service.getUser(GITHUB_ACCOUNT);
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
    private void easyRetrofit2() {
        GithubService service = GenServiceUtil.createService(GithubService.class);
        Call<GithubUserBean> call = service.getUser(GITHUB_ACCOUNT);
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

    private void lazyRetrofit2() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit
                        .Builder()
                        .baseUrl(RequestUrl.BASE_URL)
                        // add converter bean factory
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        GithubService service = retrofit.create(GithubService.class);
        Call<GithubUserBean> call = service.getUser(GITHUB_ACCOUNT);

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
        llContainer.removeAllViews();
        if (userBean != null) {
            View view = getUserView(userBean.getLogin(), userBean.getBio(), userBean.getId(),
                    userBean.getCreated_at(), userBean.getUpdated_at(), userBean.getAvatar_url());
            llContainer.addView(view);
        } else {
            Toast.makeText(this, "result is null", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setFollowersView(List<UserFollowerBean> followers) {
        llContainer.removeAllViews();
        if (followers != null && followers.size() > 0) {
            for (UserFollowerBean user : followers) {
                View view = getUserView(user.getLogin(), "Hello World!", user.getId(),
                        "", "", user.getAvatar_url());
                llContainer.addView(view);
            }
        } else {
            ToastUtil.showShort("result is null");
        }
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    private View getUserView(String name, String bio, int userId, String createTime, String updateTime, String url) {
        LogUtil.i(TAG, "url===" + url);
        View view = LayoutInflater.from(this).inflate(R.layout.item_user, null);
        TextView title = view.findViewById(R.id.title);
        TextView tvBio = view.findViewById(R.id.bio);
        TextView id = view.findViewById(R.id.userId);
        TextView tvCreateTime = view.findViewById(R.id.createTime);
        TextView tvUpdateTime = view.findViewById(R.id.updateTime);
        ImageView avatar = view.findViewById(R.id.avatar);

        title.setText("Name: " + name);
        tvBio.setText("Bio: " + bio);
        id.setText("Id: " + String.valueOf(userId));
        tvCreateTime.setText("createTime: " + createTime);
        tvUpdateTime.setText("updateTime: " + updateTime);
        Glide.with(this).load(url).into(avatar);
        return view;
    }
}
