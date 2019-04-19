package com.xpf.rxjavaretrofit2demo.ui.movie.model;

import com.xpf.android.retrofit.RetrofitHelper;
import com.xpf.android.retrofit.callback.ApiSubscriber;
import com.xpf.android.retrofit.callback.HttpRequestCallback;
import com.xpf.android.retrofit.utils.RxThreadUtils;
import com.xpf.rxjavaretrofit2demo.bean.MovieRespBean;
import com.xpf.rxjavaretrofit2demo.service.MovieService;
import com.xpf.rxjavaretrofit2demo.ui.movie.contract.MovieContract;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:
 */
public class MovieModel implements MovieContract.IModel {

    @Override
    public void getMovie(final HttpRequestCallback<MovieRespBean> callback) {
        RetrofitHelper
                .getInstance()
                .createService(MovieService.class)
                .getMovieList()
                .compose(RxThreadUtils.getScheduler())
                .subscribe(new ApiSubscriber<MovieRespBean>() {
                    @Override
                    public void onNext(MovieRespBean movieRespBean) {
                        if (callback != null) {
                            callback.onSuccess(movieRespBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (callback != null) {
                            callback.onFailed(t.getMessage());
                        }
                    }
                });
    }
}
