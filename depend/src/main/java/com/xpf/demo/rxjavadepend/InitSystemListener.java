package com.xpf.demo.rxjavadepend;

import java.io.File;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public interface InitSystemListener {
    void onSuccess(File file);

    void onFaile();
}
