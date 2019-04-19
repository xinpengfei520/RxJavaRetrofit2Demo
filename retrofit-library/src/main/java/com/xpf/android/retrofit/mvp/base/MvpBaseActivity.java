package com.xpf.android.retrofit.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xpf.android.retrofit.mvp.factory.IPresenterFactory;
import com.xpf.android.retrofit.mvp.factory.PresenterFactoryImpl;
import com.xpf.android.retrofit.mvp.proxy.IPresenterProxy;
import com.xpf.android.retrofit.mvp.proxy.PresenterProxy;

import butterknife.ButterKnife;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:MvpBaseActivity
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public abstract class MvpBaseActivity<V extends IBaseView, P extends MvpBasePresenter<V>>
        extends AppCompatActivity implements IPresenterProxy<V, P> {

    private static final String PRESENTER_SAVE_KEY_ACTIVITY = "presenter_save_key_activity";
    /**
     * 创建被代理对象,传入默认 Presenter 的工厂
     */
    private PresenterProxy<V, P> mProxy = new PresenterProxy<>(PresenterFactoryImpl.<V, P>createFactory(getClass()));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY_ACTIVITY));
        }
        setContentView(getViewLayoutId());
        ButterKnife.bind(this);
        mProxy.onCreate((V) this);
        initData(savedInstanceState);
    }

    /**
     * 获取 View 视图的 id
     *
     * @return
     */
    public abstract int getViewLayoutId();

    public abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY_ACTIVITY, mProxy.onSaveInstanceState());
    }

    /**
     * 子类可以重新该方法实现自己的 Presenter 工厂
     *
     * @param presenterFactory
     */
    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    /**
     * 子类可以重新该方法获取自己的 Presenter 工厂
     *
     * @return
     */
    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    /**
     * 子类可以重新该方法获取 Presenter
     *
     * @return
     */
    @Override
    public P getPresenter() {
        return mProxy.getPresenter();
    }
}
