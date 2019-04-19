package com.xpf.rxjavaretrofit2demo.ui.movie.presenter;

import com.xpf.android.retrofit.callback.HttpRequestCallback;
import com.xpf.android.retrofit.mvp.base.MvpBasePresenter;
import com.xpf.rxjavaretrofit2demo.bean.MovieRespBean;
import com.xpf.rxjavaretrofit2demo.ui.movie.contract.MovieContract;
import com.xpf.rxjavaretrofit2demo.ui.movie.model.MovieModel;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:
 */
public class MoviePresenter extends MvpBasePresenter<MovieContract.IView> implements MovieContract.IPresenter {

    private static final String TAG = "MoviePresenter";
    private MovieContract.IModel mModel = new MovieModel();

    @Override
    public void getMovie() {
        getView().onLoading(true);
        mModel.getMovie(new HttpRequestCallback<MovieRespBean>() {
            @Override
            public void onSuccess(MovieRespBean movieRespBean) {
                getView().onLoading(false);
                if (movieRespBean != null) {
                    getView().onMovieResult(movieRespBean);
                }
            }

            @Override
            public void onFailed(String errMsg) {
                getView().onLoading(false);
                getView().onLoadFailed(errMsg);
            }
        });
    }
}
