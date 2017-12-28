package com.xpf.rxjavaretrofit2demo.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.api.GithubService;
import com.xpf.rxjavaretrofit2demo.api.Request;
import com.xpf.rxjavaretrofit2demo.utils.ToastUtil;
import com.xpf.rxjavaretrofit2demo.view.XDialog;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SimpleRetrofit extends AppCompatActivity {

    private static final String TAG = SimpleRetrofit.class.getSimpleName();
    private TextView textView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_retrofit);
        textView = findViewById(R.id.textView);
        progressDialog = XDialog.create(this);

        Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        if (!TextUtils.isEmpty(account)) {
            progressDialog.show();
            simpleRetrofit(account);
        }
    }

    private void simpleRetrofit(String account) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        Retrofit.Builder builder =
                new Retrofit
                        .Builder()
                        .baseUrl(Request.BASE_URL);
        Retrofit retrofit = builder.client(httpClient.build()).build();
        GithubService githubService = retrofit.create(GithubService.class);
        Call<ResponseBody> call = githubService.getUserString(account);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String json = response.body().string();
                    parseJson(json);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showShort("not find such user!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.t(TAG).e(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void parseJson(String json) {
        if (!TextUtils.isEmpty(json)) {
            Logger.t(TAG).json(json);
            textView.setText(json);
        }
    }
}
