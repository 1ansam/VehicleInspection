package com.yxf.vehicleinspection.singleton

import android.content.Context
import android.content.SharedPreferences
import com.yxf.vehicleinspection.MyApp

/**
 *   author:yxf
 *   time:2021/10/12
 *   sp单例
 */
object SharedP {
    val instance: SharedPreferences = MyApp.context.getSharedPreferences("yxfSharedP", Context.MODE_PRIVATE)
}