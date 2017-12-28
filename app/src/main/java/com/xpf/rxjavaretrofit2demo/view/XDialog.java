package com.xpf.rxjavaretrofit2demo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by xpf on 2017/12/28 :)
 * Function:
 */

public class XDialog {

    public static ProgressDialog create(@NonNull Context context) {
        ProgressDialog loading = new ProgressDialog(context);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setIndeterminate(true);
        loading.setInverseBackgroundForced(true);
        loading.setMessage("loading...");
        return loading;
    }
}
