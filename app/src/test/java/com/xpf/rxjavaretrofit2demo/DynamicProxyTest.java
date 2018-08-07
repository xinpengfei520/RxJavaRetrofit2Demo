package com.xpf.rxjavaretrofit2demo;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by x-sir on 2018/8/7 :)
 * Function:
 */
public class DynamicProxyTest {

    interface Weather {
        @GET("/v3/weather/weatherInfo")
        Call<ResponseBody> get(@Query("city") String city, @Query("key") String key);

        @FormUrlEncoded
        @POST("/v3/weather/weatherInfo")
        Call<ResponseBody> post(@Field("city") String city, @Field("key") String key);
    }

    @Test
    public void proxyTest() {
        Weather weather = (Weather) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Weather.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName()); // 拿到方法名
                System.out.println(Arrays.toString(args)); // 拿到方法的参数列表
                return null;
            }
        });

        weather.get("hello", "world");
    }
}
