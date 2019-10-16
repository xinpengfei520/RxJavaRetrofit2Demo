package com.xpf.android.mvvm.activity

import com.xpf.android.mvvm.BR
import com.xpf.android.mvvm.R
import com.xpf.android.mvvm.base.BaseActivity
import com.xpf.android.mvvm.bean.SearchBeans
import com.xpf.android.mvvm.constants.IntentConstants
import com.xpf.android.mvvm.databinding.ActivityDetailBinding
import com.xpf.android.mvvm.viewmodel.DetailViewModel

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:详情页面
 */
class DetailActivity : BaseActivity<ActivityDetailBinding, SearchBeans, DetailViewModel>() {

    private val searchBeans: SearchBeans by lazy {
        SearchBeans()
    }

    override fun initViews() = R.layout.activity_detail

    override fun variableId() = BR.searchBeans

    override fun model() = searchBeans

    override fun viewModel() = DetailViewModel(searchBeans)

    override fun initParams() {

    }

    override fun loadData() {
        viewModel?.update(intent.getStringExtra(IntentConstants.DATA))
    }
}