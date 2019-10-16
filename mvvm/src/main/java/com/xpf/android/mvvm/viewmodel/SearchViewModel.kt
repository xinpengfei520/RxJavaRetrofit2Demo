package com.xpf.android.mvvm.viewmodel

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xpf.android.mvvm.bean.SearchBeans
import com.xpf.android.mvvm.net.NetRequest
import org.greenrobot.eventbus.EventBus

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class SearchViewModel {
    @SuppressLint("CheckResult")
    fun getRemoteValue() {
        NetRequest.getInstance().getRequestInfo().subscribe({
            val type = object : TypeToken<List<SearchBeans>>() {}.type
            val value = Gson().fromJson<List<SearchBeans>>(it, type)
            EventBus.getDefault().post(value)
        }, {

        }, {

        })
    }
}