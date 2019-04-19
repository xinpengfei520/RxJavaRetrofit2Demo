package com.xpf.android.retrofit.mvp.proxy;

import android.os.Bundle;

import com.xpf.android.retrofit.mvp.base.MvpBasePresenter;
import com.xpf.android.retrofit.mvp.base.IBaseView;
import com.xpf.android.retrofit.mvp.factory.IPresenterFactory;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:代理实现类，用来管理 Presenter 的生命周期，还有和 view 之间的关联
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class PresenterProxy<V extends IBaseView, P extends MvpBasePresenter<V>> implements IPresenterProxy<V, P> {

    /**
     * 获取 onSaveInstanceState 中 bundle 的 key
     */
    private static final String PRESENTER_KEY = "presenter_key";
    /**
     * Presenter 工厂类
     */
    private IPresenterFactory<V, P> mFactory;
    private P mPresenter;
    private Bundle mBundle;
    private boolean mIsBindView;

    public PresenterProxy(IPresenterFactory<V, P> mFactory) {
        this.mFactory = mFactory;
    }

    /**
     * 设置 Presenter 的工厂类,这个方法只能在创建 Presenter 之前调用，也就是调用 getPresenter() 之前，如果 Presenter 已经创建则不能再修改
     *
     * @param presenterFactory IPresenterFactory 类型
     */
    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("setPresenterFactory() can only be called before getPresenter() be called！");
        }
        this.mFactory = presenterFactory;
    }

    /**
     * 获取 Presenter 工厂类
     *
     * @return
     */
    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mFactory;
    }

    @Override
    public P getPresenter() {
        if (mFactory != null) {
            if (mPresenter == null) {
                mPresenter = mFactory.createPresenter();
            }
        }
        return mPresenter;
    }

    /**
     * 绑定 Presenter 和 view
     *
     * @param mvpView
     */
    public void onCreate(V mvpView) {
        getPresenter();
        if (mPresenter != null && !mIsBindView) {
            mPresenter.onBindView(mvpView);
            mIsBindView = true;
        }
    }

    /**
     * 销毁 Presenter 持有的 View
     */
    private void onUnbindView() {
        if (mPresenter != null && mIsBindView) {
            mPresenter.onUnbindView();
            mIsBindView = false;
        }
    }

    /**
     * 销毁 Presenter
     */
    public void onDestroy() {
        if (mPresenter != null) {
            onUnbindView();
            mPresenter.onDestroyPresenter();
            mPresenter = null;
        }
    }

    /**
     * 意外销毁的时候调用
     *
     * @return Bundle，存入回调给 Presenter 的 Bundle 和当前 Presenter 的 id
     */
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getPresenter();
        if (mPresenter != null) {
            Bundle presenterBundle = new Bundle();
            // 回调 Presenter
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY, presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复 Presenter
     *
     * @param savedInstanceState 意外关闭时存储的 Bundle
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }

}
