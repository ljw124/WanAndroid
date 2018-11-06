package com.dcdz.wanandroid.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

/**
 * Created by LJW on 2018/11/6.
 */
open class BaseModel : IModel, LifecycleObserver {

    override fun onDestroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy(owner: LifecycleOwner){
        owner.lifecycle.removeObserver(this)
    }
}