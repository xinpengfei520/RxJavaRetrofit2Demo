package com.xpf.android.retrofit.loader;

import com.xpf.android.retrofit.response.BaseResponse;
import com.xpf.android.retrofit.response.FaultException;

import io.reactivex.functions.Function;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:
 */
public class PayLoad<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(BaseResponse<T> tBaseResponse) throws Exception {
        // 当获取数据失败时，封装一个 FaultException 抛出去
        if (!tBaseResponse.isSuccess()) {
            try {
                throw new FaultException(tBaseResponse.status, tBaseResponse.message);
            } catch (FaultException e) {
                e.printStackTrace();
            }
        }
        return tBaseResponse.data;
    }
}
