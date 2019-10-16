package com.xpf.android.mvvm.viewmodel

import com.xpf.android.mvvm.bean.SearchBeans

/**
 * Created by x-sir on 2019-10-16 :)
 * Function:
 */
class DetailViewModel constructor(val beans: SearchBeans) {
    fun update(value: String) {
        beans.value = value
    }
}