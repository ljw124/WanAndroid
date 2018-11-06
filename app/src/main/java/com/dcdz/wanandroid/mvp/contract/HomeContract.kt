package com.dcdz.wanandroid.mvp.contract

import com.dcdz.wanandroid.bean.ArticleResponseBody
import com.dcdz.wanandroid.bean.Banner

/**
 * Created by LJW on 2018/11/6.
 */
interface HomeContract {

    interface View : CommonContract.View {
        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticles(articles: ArticleResponseBody)
    }

    interface Presenter: CommonContract.Presenter<View> {
        fun requestHomeData()

        fun requestBanner()

        fun requestArticles(num: Int)
    }
}