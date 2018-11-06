package com.dcdz.wanandroid.http.exception

import com.google.gson.JsonParseException
import org.apache.log4j.Logger
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by LJW on 2018/11/6.
 */
class ExceptionHandle {

    companion object {
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "请求失败，请稍后重试"
        internal var logger = Logger.getLogger(ExceptionHandle::class.java!!)

        fun handleException(e: Throwable): String {
            e.printStackTrace()
            if (e is SocketTimeoutException) {//网络超时
                logger.error( "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is ConnectException) { //均视为网络错误
                logger.error( "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException) {   //均视为解析错误
                logger.error( "数据解析异常: " + e.message)
                errorMsg = "数据解析异常"
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is ApiException) {//服务器返回的错误信息
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is UnknownHostException) {
                logger.error( "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误"
                errorCode = ErrorStatus.SERVER_ERROR
            } else {//未知错误
                try {
                    logger.error("错误: " + e.message)
                } catch (e1: Exception) {
                    logger.error("未知错误Debug调试 ")
                }

                errorMsg = "未知错误，可能抛锚了吧~"
                errorCode = ErrorStatus.UNKNOWN_ERROR
            }
            return errorMsg
        }

    }
}