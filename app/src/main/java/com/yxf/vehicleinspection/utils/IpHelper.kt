package com.yxf.vehicleinspection.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log

/**
 *   author:yxf
 *   time:2021/10/11
 */
object IpHelper {
    fun getIpAddress(context: Context) : String {
        val wifiManager : WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled){
            val wifiInfo = wifiManager.connectionInfo
            val ipAddress = wifiInfo.ipAddress
            return int2Ip(ipAddress)

        }else return ""

    }
    private fun int2Ip(ipAddress : Int) : String{
        return "${ipAddress and 0XFF}.${ipAddress shr 8 and 0XFF}.${ipAddress shr 16 and 0XFF}.${ipAddress shr 24 and 0XFF}."
    }
}