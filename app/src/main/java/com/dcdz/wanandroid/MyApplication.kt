package com.dcdz.wanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.dcdz.wanandroid.utils.DisplayManager
import com.dcdz.wanandroid.utils.SettingUtil
import com.dcdz.wanandroid.utils.log.CrashHandler
import com.dcdz.wanandroid.utils.log.Log4jConfigure
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.crashreport.CrashReport
import org.litepal.LitePal
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by LJW on 2018/11/5.
 */
class MyApplication : MultiDexApplication() {

    protected var log = org.apache.log4j.Logger.getLogger(MyApplication::class.java!!)
    private var refWatcher: RefWatcher? = null

    companion object {
        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher?{
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context =  applicationContext
        DisplayManager.init(this)
        refWatcher = setupLeakCanary()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initTheme()

        //初始化数据库
        LitePal.initialize(this)

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        val crashHandler = CrashHandler.getInstance()
        crashHandler.init(this)
        object : Thread() {
            override fun run() {
                Log4jConfigure.configure(filesDir.absolutePath)
                log.info("configure log4j ok")
            }
        }.start()

        //初始化腾讯Bugly
        //参数2：APPID，平台注册时得到; 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        CrashReport.initCrashReport(applicationContext, "5ca6c7757f", false)
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            log.info("onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            log.info("onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            log.info("onDestroy: " + activity.componentName.className)
        }
    }

    /**
     * 初始化主题
     */
    private fun initTheme(){
        if (SettingUtil.getIsAutoNightMode()){//自动切换主题
            val nightStartHour = SettingUtil.getNightStartHour().toInt()
            val nightStartMinute = SettingUtil.getNightStartMinute().toInt()
            val dayStartHour = SettingUtil.getDayStartHour().toInt()
            val dayStartMinute = SettingUtil.getDayStartMinute().toInt()

            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val nightValue = nightStartHour * 60 * nightStartMinute
            val dayValue = dayStartHour * 60 + dayStartMinute
            val currentValue = currentHour * 60 + currentMinute

            if (currentValue >= nightValue || currentValue <= dayValue){//夜晚
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SettingUtil.setIsNightMode(true)
            } else {//白天
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SettingUtil.setIsNightMode(false)
            }
        } else {//根据设置来判断当前的主题
            if (SettingUtil.getIsNightMode()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}