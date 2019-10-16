package com.xpf.android.mvvm.utils

import com.xpf.android.mvvm.MyApplication

import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:线程调度
 */
class RxThreadUtils {

    companion object {

        /**
         * Flowable 切换到主线程
         */
        @JvmStatic
        fun <T> flowableToMain(): FlowableTransformer<T, T> {
            return FlowableTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        /**
         * Observable 切换到主线程
         */
        @JvmStatic
        fun <T> observableToMain(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        /**
         * Maybe 切换到主线程
         */
        @JvmStatic
        fun <T> maybeToMain(): MaybeTransformer<T, T> {
            return MaybeTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        /**
         * Single 切换到主线程
         */
        @JvmStatic
        fun <T> singleToMain(): SingleTransformer<T, T> {
            return SingleTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        /**
         * 过滤 RxPermission 通过权限的的 Transformer
         */
        @JvmStatic
        fun filterGranted(): ObservableTransformer<Boolean, Boolean> {
            return ObservableTransformer { upstream ->
                upstream.map { aBoolean ->
                    if (!aBoolean) {
                        ToastUtils.showShort(MyApplication.instance, "您拒绝了权限！")
                    }
                    aBoolean
                }.filter { aBoolean -> aBoolean }
            }
        }
    }
}
