package com.dcdz.wanandroid.http

import com.dcdz.wanandroid.BuildConfig
import com.dcdz.wanandroid.MyApplication
import com.dcdz.wanandroid.api.ApiService
import com.dcdz.wanandroid.api.Constant
import com.dcdz.wanandroid.api.HttpConstant
import com.dcdz.wanandroid.http.interceptor.CacheInterceptor
import com.dcdz.wanandroid.http.interceptor.HeaderInterceptor
import com.dcdz.wanandroid.http.interceptor.SaveCookieInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by LJW on 2018/11/6.
 * Retrofit封装网络请求
 */
object RetrofitHelper {

    private var retrofit: Retrofit? = null

    private fun getOkHttpClient(): OkHttpClient{
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG){
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        //设置请求的缓存的大小跟位置
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeaderInterceptor())
            addInterceptor(SaveCookieInterceptor())
            addInterceptor(CacheInterceptor())
            cache(cache)
            connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
        }
        return builder.build()
    }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null){
            synchronized(RetrofitHelper::class.java){
                if (retrofit == null){
                    retrofit = Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .client(getOkHttpClient())
                            .addConverterFactory(MoshiConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }

    val service: ApiService by lazy {
        getRetrofit()!!.create(ApiService::class.java)
    }
}