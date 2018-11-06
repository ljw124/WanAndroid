package com.dcdz.wanandroid.mvp.model

import com.dcdz.wanandroid.bean.Article
import com.dcdz.wanandroid.bean.ArticleResponseBody
import com.dcdz.wanandroid.bean.Banner
import com.dcdz.wanandroid.bean.HttpResult
import com.dcdz.wanandroid.http.RetrofitHelper
import com.dcdz.wanandroid.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LJW on 2018/11/6.
 */
class HomeModel : CommonModel() {

    /**
     * 获取首页 Banner 数据
     */
    fun requestBanner(): Observable<HttpResult<List<Banner>>> {
        return RetrofitHelper.service.getBanners()
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取首页置顶文章
     */
    fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>>{
        return RetrofitHelper.service.getTopArticles()
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取首页文章列表
     */
    fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>>{
        return RetrofitHelper.service.getArticles(num)
                .compose(SchedulerUtils.ioToMain())
    }
}