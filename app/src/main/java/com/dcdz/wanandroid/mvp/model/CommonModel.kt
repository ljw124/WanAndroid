package com.dcdz.wanandroid.mvp.model

import com.dcdz.wanandroid.base.BaseModel
import com.dcdz.wanandroid.bean.HttpResult
import com.dcdz.wanandroid.http.RetrofitHelper
import com.dcdz.wanandroid.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by ljw on 2018/11/6.
 */
open class CommonModel : BaseModel() {

    fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.addCollectArticle(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.cancelCollectArticle(id)
                .compose(SchedulerUtils.ioToMain())
    }

}