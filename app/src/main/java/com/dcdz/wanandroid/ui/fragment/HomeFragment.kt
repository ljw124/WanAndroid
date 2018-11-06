package com.dcdz.wanandroid.ui.fragment

import com.dcdz.wanandroid.R
import com.dcdz.wanandroid.base.BaseFragment
import com.dcdz.wanandroid.bean.ArticleResponseBody
import com.dcdz.wanandroid.bean.Banner
import com.dcdz.wanandroid.mvp.contract.HomeContract

/**
 * Created by LJW on 2018/11/6.
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun scrollToTop() {
    }

    override fun setBanner(banners: List<Banner>) {
    }

    override fun setArticles(articles: ArticleResponseBody) {
    }

    override fun showCollectSuccess(success: Boolean) {
    }

    override fun showCancelCollectSuccess(success: Boolean) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
    }
}