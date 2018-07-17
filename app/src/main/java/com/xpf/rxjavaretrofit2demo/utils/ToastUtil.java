package com.xpf.rxjavaretrofit2demo.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.xpf.rxjavaretrofit2demo.MyApplication;

/**
 * Created by xpf on 2017/12/28 :)
 * Function:
 */

public class ToastUtil {

    public static void showShort(String toast) {
        if (!TextUtils.isEmpty(toast)) {
            Toast.makeText(MyApplication.getAppContext(), toast, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(String toast) {
        if (!TextUtils.isEmpty(toast)) {
            Toast.makeText(MyApplication.getAppContext(), toast, Toast.LENGTH_LONG).show();
        }
    }
}
