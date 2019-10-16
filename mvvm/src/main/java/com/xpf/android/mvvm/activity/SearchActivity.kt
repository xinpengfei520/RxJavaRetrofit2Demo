package com.xpf.android.mvvm.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.xpf.android.mvvm.BR
import com.xpf.android.mvvm.R
import com.xpf.android.mvvm.adapter.SearchAdapter
import com.xpf.android.mvvm.base.BaseActivity
import com.xpf.android.mvvm.bean.SearchBeans
import com.xpf.android.mvvm.constants.IntentConstants
import com.xpf.android.mvvm.databinding.ActivitySearchBinding
import com.xpf.android.mvvm.listener.EventListener
import com.xpf.android.mvvm.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchAdapter, SearchViewModel>(), EventListener {

    private val beans: ArrayList<SearchBeans> by lazy {
        ArrayList<SearchBeans>()
    }

    private val adapter: SearchAdapter by lazy {
        SearchAdapter(beans, this)
    }

    override fun onClickListener(view: View, value: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(IntentConstants.DATA, value)
        startActivity(intent)
    }

    override fun initViews() = R.layout.activity_search

    override fun variableId() = BR.adapter

    override fun model() = adapter

    override fun viewModel() = SearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initParams() {

    }

    override fun loadData() {
        viewDataBinding?.rvSearch?.post {
            viewModel?.getRemoteValue()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(bean: List<SearchBeans>) {
        progressBar?.visibility = View.GONE
        beans.addAll(bean)
        adapter.notifyDataSetChanged()
    }
}