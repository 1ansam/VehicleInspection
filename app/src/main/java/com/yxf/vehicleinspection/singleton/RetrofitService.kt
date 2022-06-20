package com.yxf.vehicleinspection.singleton

import com.yxf.vehicleinspection.base.BaseUrlHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *   author:yxf
 *   time:2021/9/29
 *   retrofit 单例
 */
object RetrofitService {


    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BaseUrlHelper.instance.httpUrl).build()
    }


    fun <T> create (clazz: Class<T>): T {
        return getRetrofit().create(clazz)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .build()


}