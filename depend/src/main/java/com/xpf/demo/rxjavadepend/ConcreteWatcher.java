package com.xpf.demo.rxjavadepend;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public class ConcreteWatcher implements Watcher {

    @Override
    public void update(String str) {
        System.out.println(str);
    }

}