package com.dcdz.wanandroid.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dcdz.wanandroid.api.Constant
import com.dcdz.wanandroid.event.NetworkChangeEvent
import com.dcdz.wanandroid.utils.NetworkUtil
import com.dcdz.wanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * Created by LJW on 2018/11/5.
 */
class NetworkChangeReceiver : BroadcastReceiver(){

    private var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetworkUtil.isNetworkConnected(context)
        if (isConnected){
            if (hasNetwork){
                if (isConnected != hasNetwork) {
                    EventBus.getDefault().post(NetworkChangeEvent(isConnected))
                }
            } else {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        }
    }

}