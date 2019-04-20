package com.xpf.android.retrofit.utils;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by x-sir on 2018/8/3 :)
 * Function:
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class ConverterUtil {

    /**
     * post request object serializable to json string.
     *
     * @param object object
     * @return RequestBody
     */
    public static RequestBody getRequestBody(Object object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(object));
    }
}
