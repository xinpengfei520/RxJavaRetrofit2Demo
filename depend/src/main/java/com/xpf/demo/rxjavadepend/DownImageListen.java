package com.xpf.demo.rxjavadepend;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public interface DownImageListen {
    void onScuccess(Bitmap bitmap);

    void onFail();
}
