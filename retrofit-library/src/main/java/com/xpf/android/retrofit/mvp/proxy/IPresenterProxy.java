package com.xpf.android.retrofit.mvp.proxy;

import com.xpf.android.retrofit.mvp.base.MvpBasePresenter;
import com.xpf.android.retrofit.mvp.base.IBaseView;
import com.xpf.android.retrofit.mvp.factory.IPresenterFactory;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:定义一个代理接口，提供设置工厂、获取工厂、获取 Presenter 的方法，子类可以自己实现创建工厂的方法
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public interface IPresenterProxy<V extends IBaseView, P extends MvpBasePresenter<V>> {

    /**
     * 设置创建Presenter的工厂
     *
     * @param wanPresenterFactory 类型
     */
    void setPresenterFactory(IPresenterFactory<V, P> wanPresenterFactory);

    /**
     * 获取Presenter的工厂类
     *
     * @return IPresenterFactory
     */
    IPresenterFactory<V, P> getPresenterFactory();

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     */
    P getPresenter();

}
