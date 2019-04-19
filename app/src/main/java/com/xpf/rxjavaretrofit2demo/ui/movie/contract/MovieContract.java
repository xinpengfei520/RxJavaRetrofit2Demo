package com.xpf.rxjavaretrofit2demo.ui.movie.contract;

import com.xpf.android.retrofit.callback.HttpRequestCallback;
import com.xpf.android.retrofit.mvp.base.IBaseView;
import com.xpf.rxjavaretrofit2demo.bean.MovieRespBean;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:
 */
public interface MovieContract {

    interface IModel {
        void getMovie(HttpRequestCallback<MovieRespBean> callback);
    }

    interface IView extends IBaseView {

        void onLoading(boolean show);

        void onMovieResult(MovieRespBean movieRespBean);

        void onLoadFailed(String errMsg);
    }

    interface IPresenter {

        void getMovie();
    }
}
