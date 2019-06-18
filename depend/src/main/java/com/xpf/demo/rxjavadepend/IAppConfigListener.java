package com.xpf.demo.rxjavadepend;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public interface IAppConfigListener {
    void onSuccess(List<AppConfig> list);

    void getWanUrl(String wanUrl);

    void onFail(int code);
}
