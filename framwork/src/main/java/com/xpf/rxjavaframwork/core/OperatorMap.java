package com.xpf.rxjavaframwork.core;

/**
 * Created by Administrator on 2017/2/15 0015.
 * 介绍人
 */
public class OperatorMap<T, R> implements Operator<R, T> {
    /**
     * 外部传进来的，
     * 到底是如何将  羊变成铁   这里不管
     */
    Function<? super T, ? extends R> transform;

    public OperatorMap(Function<? super T, ? extends R> transform) {
        this.transform = transform;
    }

    /**
     * 把真正铸铁的丢给放羊的
     *
     * @param subscrible
     * @return
     */
    @Override
    public Subscrible<? super T> call(Subscrible<? super R> subscrible) {
        return new MapSubscrble<>(subscrible, transform);
    }

    /**
     * 真正铸铁的人
     *
     * @param <T>
     * @param <R>
     */
    private class MapSubscrble<T, R> extends Subscrible<T> {
        /**
         * 打铁的人
         * //闺蜜
         * actual 从外部传进来的
         */
        private Subscrible<? super R> actual;

        /**
         * 转换函数
         * 羊  T  --》铁  R
         */
        Function<? super T, ? extends R> transform;

        public MapSubscrble(Subscrible<? super R> actual, Function<? super T, ? extends R> transform) {
            this.actual = actual;
            this.transform = transform;
        }

        /**
         * T 参数  羊
         *
         * @param t
         */
        @Override
        public void onNext(T t) {
            //完成转换 t --》羊  R--》铁
            R r = transform.call(t);
            //终于得到了铁  所有人皆大欢喜
            actual.onNext(r);
        }
    }
}
