package com.xpf.demo.rxjavadepend;

/**
 * Created by Administrator on 2016/11/25 0025.
 * 抽象主题角色，watched：被观察
 */
public interface Watched {
    void addWatcher(Watcher watcher);

    void removeWatcher(Watcher watcher);

    void notifyWatchers(String str);

}