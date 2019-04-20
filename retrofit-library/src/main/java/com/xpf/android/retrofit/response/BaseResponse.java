package com.xpf.android.retrofit.response;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:网络请求基类封装
 */
public class BaseResponse<T> {

    public int status;
    public String message;
    public T data;

    public boolean isSuccess() {
        return status == 200;
    }
}
