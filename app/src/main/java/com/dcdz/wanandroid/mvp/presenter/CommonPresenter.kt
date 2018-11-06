package com.dcdz.wanandroid.mvp.presenter

import com.dcdz.wanandroid.base.BasePresenter
import com.dcdz.wanandroid.http.exception.ExceptionHandle
import com.dcdz.wanandroid.http.function.RetryWithDelay
import com.dcdz.wanandroid.mvp.contract.CommonContract
import com.dcdz.wanandroid.mvp.model.CommonModel

/**
 * Created by ljw on 2018/11/6.
 */
open class CommonPresenter<V : CommonContract.View>
    : BasePresenter<V>(), CommonContract.Presenter<V> {

    private val mModel: CommonModel by lazy {
        CommonModel()
    }

    override fun addCollectArticle(id: Int) {
        val disposable = mModel.addCollectArticle(id)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.run {
                        if (results.errorCode != 0) {
                            showError(results.errorMsg)
                        } else {
                            showCollectSuccess(true)
                        }
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(disposable)
    }

    override fun cancelCollectArticle(id: Int) {
        val disposable = mModel.cancelCollectArticle(id)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.run {
                        if (results.errorCode != 0) {
                            showError(results.errorMsg)
                        } else {
                            showCancelCollectSuccess(true)
                        }
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(disposable)
    }

}