package com.xpf.rxjavaretrofit2demo.api;

import com.xpf.rxjavaretrofit2demo.bean.ContentModel;
import com.xpf.rxjavaretrofit2demo.bean.ContentParam;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by xpf on 2016/12/16 :)
 * GitHub:xinpengfei520
 * Function:define a interface
 */
public interface FlowableService {

    @GET("users/{user}")
    Flowable<ContentModel> loadVideoContent(ContentParam param);

}
