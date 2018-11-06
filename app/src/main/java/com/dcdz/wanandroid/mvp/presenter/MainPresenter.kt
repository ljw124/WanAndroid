package com.dcdz.wanandroid.mvp.presenter

import com.dcdz.wanandroid.base.BasePresenter
import com.dcdz.wanandroid.http.exception.ExceptionHandle
import com.dcdz.wanandroid.mvp.contract.MainContract
import com.dcdz.wanandroid.mvp.model.MainModel

/**
 * Created by LJW on 2018/11/6.
 */
class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter{

    private val mainModel: MainModel by lazy { MainModel() }

    override fun logout() {
        mView?.showLoading()
        val disposable = mainModel.logout()
                .subscribe({ results ->
                    mView?.apply {
                        if (results.errorCode != 0){
                            showError(results.errorMsg)
                        } else {
                            showLogoutSuccess(success = true)
                        }
                        hideLoading()
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