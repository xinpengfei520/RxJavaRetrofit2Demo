package com.xpf.android.retrofit.api;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by x-sir on 2018/8/3 :)
 * Function:
 */
public class Converter {

    public static RequestBody mapToBody(HashMap<String, String> map) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(map));
    }

    /**
     * post request object serializable to json string.
     * @param object
     * @return
     */
    public static RequestBody toBody(Object object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(object));
    }
}
