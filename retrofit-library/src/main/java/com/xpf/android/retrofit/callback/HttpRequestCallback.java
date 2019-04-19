/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.xpf.android.retrofit.callback;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:Http 请求的统一回调接口
 */
public interface HttpRequestCallback<T> {

    void onSuccess(T t);

    void onFailed(String errMsg);
}
