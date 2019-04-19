package com.xpf.android.retrofit.mvp.factory;

import com.xpf.android.retrofit.mvp.base.MvpBasePresenter;
import com.xpf.android.retrofit.mvp.annotation.CreatePresenter;
import com.xpf.android.retrofit.mvp.base.IBaseView;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:PresenterFactoryImpl 的实现类
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class PresenterFactoryImpl<V extends IBaseView, P extends MvpBasePresenter<V>> implements IPresenterFactory<V, P> {

    /**
     * 需要创建的 Presenter 的类型
     */
    private final Class<P> mPresenterClass;

    public PresenterFactoryImpl(Class<P> mPresenterClass) {
        this.mPresenterClass = mPresenterClass;
    }

    /**
     * 根据注解创建 Presenter 的工厂实现类
     *
     * @param viewClazz 需要创建 Presenter 的 V 层实现类
     * @param <V>       当前 View 实现的接口类型
     * @param <P>       当前要创建的 Presenter 类型
     * @return 工厂类
     */
    public static <V extends IBaseView, P extends MvpBasePresenter<V>> PresenterFactoryImpl<V, P> createFactory(Class<?> viewClazz) {
        CreatePresenter annotation = viewClazz.getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if (annotation != null) {
            aClass = (Class<P>) annotation.value();
        }
        return aClass == null ? null : new PresenterFactoryImpl<V, P>(aClass);
    }

    @Override
    public P createPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter create failed! please check if declaration @CreatePresenter(xxx.class) annotation or not.");
        }
    }
}
