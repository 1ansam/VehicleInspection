package com.yxf.vehicleinspection.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *   author:yxf
 *   time:2021/10/11
 */
interface WriteService {
    /**
     *   @param jkId 接口标识
     *   @param zdbs 终端标识
     *   @param jsonData json数据
     *   @return 返回通用响应体
     */
    @POST("VehicleInspection/Write")
    fun  write(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Body jsonData: String,
    ): Call<ResponseBody>
}