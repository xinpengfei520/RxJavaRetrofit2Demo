package com.xpf.rxjavaretrofit2demo;

import com.xpf.rxjavaretrofit2demo.api.GithubService;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by x-sir on 2018/8/4 :)
 * Function:
 */
public class RetrofitTest {

    @Test
    public void retrofit1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .build();

        GithubService service = retrofit.create(GithubService.class);
        Call<ResponseBody> call = service.getUserString();
        try {
            Response<ResponseBody> response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
