package com.xpf.android.retrofit.mvp.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:带有生命周期的所有 Presenter 的基类
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public abstract class MvpBasePresenter<V extends IBaseView> {

    private static final String TAG = "MvpBasePresenter";
    private V mBaseView;

    public MvpBasePresenter() {

    }

    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的 Bundle
     */
    public void onCreatePersenter(@Nullable Bundle savedState) {
        Log.i(TAG, "onCreatePresenter()");
    }

    /**
     * 绑定 View
     *
     * @param view
     */
    public void onBindView(V view) {
        this.mBaseView = view;
        Log.i(TAG, "onBindView()");
    }

    /**
     * 解绑 View，避免当请求网络数据未完成时 Activity 退出所造成的内存泄露
     */
    public void onUnbindView() {
        this.mBaseView = null;
        Log.i(TAG, "onUnbindView()");
    }

    /**
     * Presenter 被销毁时调用
     */
    public void onDestroyPresenter() {
        Log.i(TAG, "onDestroyPresenter()");
    }

    /**
     * 在 Presenter 意外销毁的时候被调用，它的调用时机和 Activity、Fragment、View 中的 onSaveInstanceState 的时机相同
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState()");
    }

    public V getView() {
        return mBaseView;
    }
}
