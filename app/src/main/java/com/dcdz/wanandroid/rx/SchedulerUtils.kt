package com.dcdz.wanandroid.rx

/**
 * Created by LJW on 2018/10/26.
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}