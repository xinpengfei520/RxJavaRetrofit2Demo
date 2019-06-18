package com.xpf.rxjavaframwork.core;

/**
 * Created by Administrator on 2017/2/15 0015.
 * 抽象的换的行为
 * T 代表 养羊人的角色
 * R  代表 打铁人的角色
 */

public interface Function<T, R> {
    R call(T t);
}
