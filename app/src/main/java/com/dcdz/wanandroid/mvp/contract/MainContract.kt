package com.dcdz.wanandroid.mvp.contract

import com.dcdz.wanandroid.base.IPresenter
import com.dcdz.wanandroid.base.IView

/**
 * Created by LJW on 2018/11/6.
 */
interface MainContract {

    interface View : IView {
        fun showLogoutSuccess(success: Boolean)
    }

    interface Presenter: IPresenter<View> {
        fun logout()
    }
}