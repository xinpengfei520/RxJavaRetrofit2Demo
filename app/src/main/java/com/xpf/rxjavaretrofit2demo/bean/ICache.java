package com.xpf.rxjavaretrofit2demo.bean;

import java.io.Serializable;

/**
 * Created by x-sir on 2019-06-02 :)
 * Function:
 */
public interface ICache {

    void put(String key, Serializable serializable);

    Serializable get(String key);
}
