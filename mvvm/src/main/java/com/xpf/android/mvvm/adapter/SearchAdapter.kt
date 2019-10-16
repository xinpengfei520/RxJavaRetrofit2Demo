package com.xpf.android.mvvm.adapter

import com.xpf.android.mvvm.BR
import com.xpf.android.mvvm.R
import com.xpf.android.mvvm.base.BaseAdapter
import com.xpf.android.mvvm.bean.SearchBeans
import com.xpf.android.mvvm.databinding.AdapterSearchBinding
import com.xpf.android.mvvm.listener.EventListener

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class SearchAdapter(beans: ArrayList<SearchBeans>, eventListener: EventListener)
    : BaseAdapter<AdapterSearchBinding, SearchBeans>(beans, eventListener) {

    override fun dataVariableId(): Int {
        return BR.beans
    }

    override fun eventListenerVariableId(): Int {
        return BR.eventListener
    }

    override fun initViews() = R.layout.adapter_search
}