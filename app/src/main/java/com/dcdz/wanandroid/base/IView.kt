package com.dcdz.wanandroid.base

/**
 * Created by LJW on 2018/11/6.
 */
interface IView {

    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg: String)
}