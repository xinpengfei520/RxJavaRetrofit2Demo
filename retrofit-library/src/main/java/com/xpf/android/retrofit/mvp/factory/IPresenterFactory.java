package com.xpf.android.retrofit.mvp.factory;

import com.xpf.android.retrofit.mvp.base.MvpBasePresenter;
import com.xpf.android.retrofit.mvp.base.IBaseView;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:定义工厂接口，提供了创建 Presenter 的接口方法
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public interface IPresenterFactory<V extends IBaseView, P extends MvpBasePresenter<V>> {

    P createPresenter();
}
