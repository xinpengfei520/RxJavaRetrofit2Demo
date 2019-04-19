package com.xpf.android.retrofit.mvp.annotation;

import com.xpf.android.retrofit.mvp.base.MvpBasePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:定义一个创建 Presenter 的注解
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<? extends MvpBasePresenter> value();
}
