package com.xpf.rxjavaframwork.core;

/**
 * Created by Administrator on 2017/2/13 0013.
 * Observable 集市， T 代表打铁
 */

public class Observable<T> {
    // 养羊的人
    private OnSubscriable<T> onSubscriable;

    public Observable(OnSubscriable<T> onSubscriable) {
        this.onSubscriable = onSubscriable;
    }

    /**
     * 实例化买卖羊的集市
     *
     * @param onSubscriable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> create(OnSubscriable<T> onSubscriable) {
        return new Observable<>(onSubscriable);
    }

    /**
     * 铁匠来到集市
     *
     * @param subscrible
     */
    public void subscrible(Subscrible<? super T> subscrible) {
        //养羊的   1
        //卖铁的人 2
        onSubscriable.call(subscrible);
    }

    /**
     * 实例化 打铁的集市
     *
     * @param func 转换的函数   羊--》铁
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Function<? super T, ? extends R> func) {
        //OperatorMap  作为介绍人
        return lift(new OperatorMap<T, R>(func));
    }

    private <R> Observable<R> lift(OperatorMap<T, R> trOperatorMap) {
        //代码运行在这里面的时候 是买羊的集市

        //OnSubsricbleLift  作为卖铁的人
        return new Observable<>(new OnSubsricbleLift<>(onSubscriable, trOperatorMap));
    }
}
