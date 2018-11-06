package com.dcdz.wanandroid.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.apache.log4j.Logger
import org.greenrobot.eventbus.EventBus

/**
 * Created by LJW on 2018/11/6.
 */
abstract class BasePresenter<V : IView> : IPresenter<V>, LifecycleObserver {

    internal var log = Logger.getLogger(BasePresenter::class.java!!)

    var mView: V? = null
        private set

    //判断是否绑定了 View
    private val isVeiwAttached: Boolean
        get() = mView != null

    private var mCompositeDisposable: CompositeDisposable? = null

    open fun useEventBus(): Boolean = false

    override fun attachView(mView: V) {
        mCompositeDisposable = CompositeDisposable()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
        }
        if (useEventBus()){
            EventBus.getDefault().register(this)
        }
    }

    override fun detachView() {
        if (useEventBus()){
            EventBus.getDefault().unregister(this)
        }
        // 保证activity结束时取消所有正在执行的订阅
        unDispose()
        mView = null
    }

    open fun checkViewAttached(){
        if (!isVeiwAttached) throw ViewNotAttachedException()
    }

    open fun addSubscription(disposable: Disposable){
        mCompositeDisposable?.add(disposable)
    }

    private fun unDispose(){
        if (mCompositeDisposable != null){
            mCompositeDisposable?.clear()
        }
        mCompositeDisposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner){
        detachView()
        owner.lifecycle.removeObserver(this)
    }

    private class ViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}