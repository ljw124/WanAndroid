package com.dcdz.wanandroid.mvp.model

import com.dcdz.wanandroid.base.BaseModel
import com.dcdz.wanandroid.bean.HttpResult
import com.dcdz.wanandroid.http.RetrofitHelper
import com.dcdz.wanandroid.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LJW on 2018/11/6.
 */
class MainModel : BaseModel() {

    fun logout(): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.logout()
                .compose(SchedulerUtils.ioToMain())
    }
}