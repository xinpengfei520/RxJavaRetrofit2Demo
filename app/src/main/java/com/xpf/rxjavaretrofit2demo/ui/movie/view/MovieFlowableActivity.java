package com.xpf.rxjavaretrofit2demo.ui.movie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xpf.android.retrofit.mvp.annotation.CreatePresenter;
import com.xpf.android.retrofit.mvp.base.MvpBaseActivity;
import com.xpf.rxjavaretrofit2demo.R;
import com.xpf.rxjavaretrofit2demo.bean.MovieRespBean;
import com.xpf.rxjavaretrofit2demo.ui.movie.contract.MovieContract;
import com.xpf.rxjavaretrofit2demo.ui.movie.presenter.MoviePresenter;
import com.xpf.rxjavaretrofit2demo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

@CreatePresenter(MoviePresenter.class)
public class MovieFlowableActivity extends MvpBaseActivity<MovieContract.IView, MoviePresenter>
        implements MovieContract.IView {

    @BindView(R.id.tvMovieName)
    TextView tvMovieName;
    @BindView(R.id.ivMovie)
    ImageView ivMovie;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_flowable;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // TODO: 2019/4/19 deal with
        }
    }

    @OnClick(R.id.tvMovieName)
    public void onViewClicked() {
        getData();
    }

    private void getData() {
        getPresenter().getMovie();

        // 使用 Loader 方式实现
//        MovieLoader movieLoader = new MovieLoader();
//        Disposable disposable = movieLoader.getMovie().subscribe(movieRespBean -> {
//            if (movieRespBean != null) {
//                setData(movieRespBean);
//            }
//        }, throwable -> ToastUtil.showShort(throwable.getMessage()));
    }

    @Override
    public void onLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onMovieResult(MovieRespBean movieRespBean) {
        setData(movieRespBean);
    }

    private void setData(MovieRespBean movieRespBean) {
        MovieRespBean.TrailersBean trailersBean = movieRespBean.getTrailers().get(1);
        tvMovieName.setText(trailersBean.getMovieName());
        Glide.with(this).load(trailersBean.getCoverImg()).into(ivMovie);
    }

    @Override
    public void onLoadFailed(String errMsg) {
        ToastUtil.showShort(errMsg);
    }
}
