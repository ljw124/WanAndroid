package com.dcdz.wanandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dcdz.wanandroid.MyApplication
import com.dcdz.wanandroid.R

/**
 * Created by LJW on 2018/11/5.
 * 图片加载工具类
 */
object ImageLoader {

    // 1.开启无图模式 2.非WiFi环境 不加载图片
    private val isLoadImage = !SettingUtil.getIsNoPhotoMode() || NetworkUtil.isWifi(MyApplication.context)

    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     */
    fun load(context: Context?, url: String?, iv: ImageView?) {
        if (isLoadImage) {
            iv?.apply {
                Glide.with(context!!).clear(iv)
                val options = RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.bg_placeholder)
                Glide.with(context!!)
                        .load(url)
                        .transition(DrawableTransitionOptions().crossFade())
                        .apply(options)
                        .into(iv)
            }
        }

    }
}