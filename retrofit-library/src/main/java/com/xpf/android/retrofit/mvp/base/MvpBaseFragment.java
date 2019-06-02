package com.xpf.android.retrofit.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xpf.android.retrofit.mvp.factory.IPresenterFactory;
import com.xpf.android.retrofit.mvp.factory.PresenterFactoryImpl;
import com.xpf.android.retrofit.mvp.proxy.IPresenterProxy;
import com.xpf.android.retrofit.mvp.proxy.PresenterProxy;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:MvpBaseFragment
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public abstract class MvpBaseFragment<V extends IBaseView, P extends MvpBasePresenter<V>> extends Fragment implements IPresenterProxy<V, P> {

    private static final String PRESENTER_SAVE_KEY_FRAGMENT = "presenter_save_key_fragment";
    /**
     * 创建被代理对象,传入默认 Presenter 的工厂
     */
    private PresenterProxy<V, P> mProxy = new PresenterProxy<>(PresenterFactoryImpl.<V, P>createFactory(getClass()));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getViewLayoutId(), container, false);
    }

    /**
     * 获取子类布局 id
     *
     * @return
     */
    public abstract int getViewLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 初始化数据
     */
    public abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        mProxy.onCreate((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY_FRAGMENT, mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getPresenter() {
        return mProxy.getPresenter();
    }
}
