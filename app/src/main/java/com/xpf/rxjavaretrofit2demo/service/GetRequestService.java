package com.xpf.rxjavaretrofit2demo.service;

import com.xpf.rxjavaretrofit2demo.bean.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetRequestService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation> register();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation> login();

}

