package com.xpf.android.mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by x-sir on 2019-10-15 :)
 * Function:
 */
abstract class BaseActivity<T : ViewDataBinding, M : Any, VM : Any> : AppCompatActivity() {

    abstract fun initViews(): Int
    abstract fun variableId(): Int
    abstract fun model(): M
    abstract fun viewModel(): VM
    abstract fun initParams()
    abstract fun loadData()

    var viewDataBinding: T? = null
    var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, initViews())
        viewDataBinding?.setVariable(variableId(), model())

        viewModel = viewModel()

        initParams()
        loadData()
    }
}