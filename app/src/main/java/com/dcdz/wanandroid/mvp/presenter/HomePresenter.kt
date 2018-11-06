package com.dcdz.wanandroid.mvp.presenter

import com.dcdz.wanandroid.bean.Article
import com.dcdz.wanandroid.bean.ArticleResponseBody
import com.dcdz.wanandroid.bean.HttpResult
import com.dcdz.wanandroid.http.exception.ExceptionHandle
import com.dcdz.wanandroid.http.function.RetryWithDelay
import com.dcdz.wanandroid.mvp.contract.HomeContract
import com.dcdz.wanandroid.mvp.model.HomeModel
import com.dcdz.wanandroid.utils.SettingUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by LJW on 2018/11/6.
 */
class HomePresenter : CommonPresenter<HomeContract.View>(), HomeContract.Presenter {

    private val homeModel: HomeModel by lazy { HomeModel() }

    override fun requestBanner() {
        mView?.showLoading()
        val disposable = homeModel.requestBanner()
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.errorCode != 0){
                            showError(results.errorMsg)
                        } else {
                            setBanner(results.data)
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

    override fun requestHomeData() {
        mView?.showLoading()
        requestBanner()
        //判断是否显示置顶文章
        val observable = if (SettingUtil.getIsShowTopArticle()) {
            homeModel.requestArticles(0)
        } else {
            Observable.zip(homeModel.requestTopArticles(), homeModel.requestArticles(0),
                    BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleResponseBody>,
                            HttpResult<ArticleResponseBody>> { t1, t2 ->
                        t1.data.forEach {
                            // 置顶数据中没有标识，手动添加一个标识
                            it.top = "1"
                        }
                        t2.data.datas.addAll(0, t1.data)
                        t2
                    })
        }
        val disposable = observable
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.errorCode != 0) {
                            showError(results.errorMsg)
                        } else {
                            setArticles(results.data)
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

    override fun requestArticles(num: Int) {
        mView?.showLoading()
        if (num == 0)
            mView?.showLoading()
        val disposable = homeModel.requestArticles(num)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                   mView?.apply {
                       if (results.errorCode != 0) {
                           showError(results.errorMsg)
                       } else {
                           setArticles(results.data)
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