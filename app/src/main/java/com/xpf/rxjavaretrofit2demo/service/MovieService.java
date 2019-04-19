package com.xpf.rxjavaretrofit2demo.service;


import com.xpf.rxjavaretrofit2demo.bean.MovieRespBean;

import io.reactivex.Flowable;
import retrofit2.http.POST;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:MovieService
 */
public interface MovieService {

    /**
     * 获取电影列表
     *
     * @return
     */
    @POST("/PageSubArea/TrailerList.api")
    Flowable<MovieRespBean> getMovieList();
}

