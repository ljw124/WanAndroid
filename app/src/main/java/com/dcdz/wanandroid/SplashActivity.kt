package com.dcdz.wanandroid

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.dcdz.wanandroid.base.BaseActivity
import com.dcdz.wanandroid.utils.AppUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private var alphaAnimation: AlphaAnimation? = null

    override fun attachLayoutRes(): Int = R.layout.activity_splash

    override fun initData() {
        //设置版本号
        tv_version_name.text = "当前版本 V：" + AppUtils.getVerName(this)
    }

    override fun initView() {
        alphaAnimation = AlphaAnimation(0.2F, 1.0F)
        alphaAnimation?.run {
            duration = 2000
            setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(p0: Animation?) {

                }

                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    toMain()
                }
            })
        }

        iv_web_icon.startAnimation(alphaAnimation)
    }

    override fun start() {
    }

    override fun useEventBus(): Boolean = false

    override fun enableNetworkTip(): Boolean = false

    private fun toMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
