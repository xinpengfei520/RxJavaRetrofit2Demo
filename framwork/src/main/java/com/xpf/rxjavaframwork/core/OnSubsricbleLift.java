package com.xpf.rxjavaframwork.core;

/**
 * Created by Administrator on 2017/2/15 0015.
 * 卖铁的人
 * T  放羊
 * R  打铁
 */

public class OnSubsricbleLift<T, R> implements OnSubscriable<R> {
    /**
     * 卖铁的角色肯定持有放羊的人的引用
     */
    OnSubscriable<T> parent;
    //R  铁   T  羊
    Operator<? extends R, ? super T> operator;

    public OnSubsricbleLift(OnSubscriable<T> parent, Operator<? extends R, ? super T> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    //

    @Override
    public void call(Subscrible<? super R> subscrible) {
        /**
         *   卖铁的人为了满足 养羊的人的需求
         *   通过千辛万苦 间接拿到了  真正打铁的人
         *
         * st 代表打铁的人
         * parent  放羊的人
         *
         * MapSubscrble正真铸铁的人
         *  parent 放羊的人
         */
        Subscrible<? super T> st = operator.call(subscrible);
        //---------未执行---
        parent.call(st);
    }
}
