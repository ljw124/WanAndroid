package com.dcdz.wanandroid.mvp.contract

import com.dcdz.wanandroid.base.IPresenter
import com.dcdz.wanandroid.base.IView

/**
 * Created by LJW on 2018/11/6.
 */
interface CommonContract {

    interface View : IView {

        fun showCollectSuccess(success: Boolean)

        fun showCancelCollectSuccess(success: Boolean)
    }

    interface Presenter<in V : View> : IPresenter<V> {

        fun addCollectArticle(id: Int)

        fun cancelCollectArticle(id: Int)

    }

}