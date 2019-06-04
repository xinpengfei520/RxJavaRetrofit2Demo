package com.xpf.rxjavaretrofit2demo.utils;

import com.safframework.cache.Cache;

import java.io.Serializable;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by x-sir on 2019-06-04 :)
 * Function:
 */
public class RxCache {

    public static <T> FlowableTransformer<T, T> transformer(final String key, final Cache cache) {
        return upstream -> upstream.map((Function<T, T>) t -> {
            cache.put(key, (Serializable) t);
            return t;
        });
    }
}
