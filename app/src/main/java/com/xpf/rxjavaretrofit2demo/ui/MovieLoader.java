package com.xpf.rxjavaretrofit2demo.ui;

import com.xpf.android.retrofit.RetrofitHelper;
import com.xpf.android.retrofit.loader.ObjectLoader;
import com.xpf.rxjavaretrofit2demo.bean.MovieRespBean;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.http.POST;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:
 */
public class MovieLoader extends ObjectLoader {

    private MovieServiceApi mService;

    public MovieLoader() {
        mService = RetrofitHelper.getInstance().createService(MovieServiceApi.class);
    }

    public Observable<MovieRespBean> getMovie() {
        return observe(mService.getMovieList()).map(new Function<MovieRespBean, MovieRespBean>() {
            @Override
            public MovieRespBean apply(MovieRespBean movieRespBean) throws Exception {
                return movieRespBean;
            }
        });
    }

    public interface MovieServiceApi {

        /**
         * 获取电影列表
         *
         * @return
         */
        @POST("/PageSubArea/TrailerList.api")
        Observable<MovieRespBean> getMovieList();
    }
}
