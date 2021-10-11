package com.yxf.vehicleinspection.singleton

import com.google.gson.Gson

/**
 *   author:yxf
 *   time:2021/10/11
 */
object GsonSingleton {
    fun getGson() : Gson{
        return Gson()
    }
}