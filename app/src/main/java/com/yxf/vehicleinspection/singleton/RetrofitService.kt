package com.yxf.vehicleinspection.singleton

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *   author:yxf
 *   time:2021/9/29
 */
object RetrofitService {
    lateinit var  mRetrofit :Retrofit
    fun getRetrofit():Retrofit{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            "http://192.168.2.201:8084/").build()
    }


    fun <T> create (clazz: Class<T>): T {
        return getRetrofit().create(clazz)
    }
}