package com.yxf.vehicleinspection.service

import com.yxf.vehicleinspection.bean.JsCsCode
import retrofit2.Call
import retrofit2.http.GET

/**
 *   author:yxf
 *   time:2021/10/8
 */
interface JsCsCodeService {
    @GET("getJsCsCode.json")
    fun getJsCsCode() : Call<List<JsCsCode>>
}