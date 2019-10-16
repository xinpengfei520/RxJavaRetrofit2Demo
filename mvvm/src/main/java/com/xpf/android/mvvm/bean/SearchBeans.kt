package com.xpf.android.mvvm.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class SearchBeans : BaseObservable() {

    var value: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.value)
        }

    var position: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }

}