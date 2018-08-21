package com.xpf.rxjavaretrofit2demo.api;

import com.xpf.rxjavaretrofit2demo.bean.GithubUserBean;
import com.xpf.rxjavaretrofit2demo.bean.UserFollowerBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xpf on 2016/12/16 :)
 * GitHub:xinpengfei520
 * Function:define a interface
 */

public interface GithubService {

    @GET("users/{user}")
    Call<ResponseBody> getUserString(@Path("user") String user);

    @GET("users/{user}")
    Call<GithubUserBean> getUser(@Path("user") String user);

    @GET("users/{user}/followers")
    rx.Observable<List<UserFollowerBean>> followers(@Path("user") String usr);

    //--------------------- Test--------------------------------------------

    @GET("users/xinpengfei520")
    Call<ResponseBody> getUserString();


}
