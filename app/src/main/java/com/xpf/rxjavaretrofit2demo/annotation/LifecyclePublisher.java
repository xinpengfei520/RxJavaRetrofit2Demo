package com.xpf.rxjavaretrofit2demo.annotation;

/**
 * Created by x-sir on 2019-06-02 :)
 * Function:
 */
public @interface LifecyclePublisher {

    int ATTACH = 0;
    int CREATE = 1;
    int CREATE_VIEW = 2;
    int START = 3;
    int RESUME = 4;
    int PAUSE = 5;
    int STOP = 6;
    int DESTROY_VIEW = 7;
    int DESTROY = 8;
    int DETACH = 9;
    int DISPOSE = 10;

    @interface Event {

    }
}
