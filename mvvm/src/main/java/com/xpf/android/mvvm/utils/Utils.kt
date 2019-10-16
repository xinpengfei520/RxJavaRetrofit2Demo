package com.xpf.android.mvvm.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xpf.android.mvvm.adapter.SearchAdapter

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class Utils {

    companion object {
        @JvmStatic
        @BindingAdapter("adapter")
        fun setAdapter(recyclerView: RecyclerView, adapter: SearchAdapter) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }
}