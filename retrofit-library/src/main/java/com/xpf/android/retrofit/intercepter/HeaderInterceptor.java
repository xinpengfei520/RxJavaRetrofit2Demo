package com.xpf.android.retrofit.intercepter;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by x-sir on 2018/8/3 :)
 * Function:HeaderInterceptor
 * {@link # https://github.com/xinpengfei520/RxJavaRetrofit2Demo}
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        request = requestBuilder.post(RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"),
                URLDecoder.decode(bodyToString(request.body()), "UTF-8")))
                .build();
        return chain.proceed(request);
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
