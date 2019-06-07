package com.xpf.rxjavaretrofit2demo.service;

import com.xpf.rxjavaretrofit2demo.bean.PM10Model;
import com.xpf.rxjavaretrofit2demo.bean.PM25Model;
import com.xpf.rxjavaretrofit2demo.bean.SO2Model;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by x-sir on 2019-06-07 :)
 * Function:
 */
public interface WeatherService {

    /*
     * Maybe 可以看成是 Single 和 Completable 的结合，没有 onNext 方法，
     * 需要通过 onSuccess 方法来发射数据，且只能发射一个数据，发射多个，后面发的也不会处理
     */

    @GET
    Maybe<List<PM25Model>> pm25(@Url String url, @Query("city") String cityId, @Query("token") String token);

    @GET
    Maybe<List<PM10Model>> pm10(@Url String url, @Query("city") String cityId, @Query("token") String token);

    @GET
    Maybe<List<SO2Model>> so2(@Url String url, @Query("city") String cityId, @Query("token") String token);

}
