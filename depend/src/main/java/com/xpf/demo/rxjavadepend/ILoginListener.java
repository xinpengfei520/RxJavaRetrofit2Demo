package com.xpf.demo.rxjavadepend;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public interface ILoginListener {
    void onSuccuess(User user);

    void onFaile(int code);

    void onUserOther();

    void noUser(int code);

    void passWordErro();
}
