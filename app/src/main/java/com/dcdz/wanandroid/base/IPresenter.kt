package com.dcdz.wanandroid.base

/**
 * Created by LJW on 2018/11/6.
 */
interface IPresenter<in V : IView> {

    fun attachView(mView: V)

    fun detachView()
}