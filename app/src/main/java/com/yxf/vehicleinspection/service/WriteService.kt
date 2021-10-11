package com.yxf.vehicleinspection.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *   author:yxf
 *   time:2021/10/11
 */
interface WriteService {
    @POST("VehicleInspection/Write")
    fun  query(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String,
    ): Call<ResponseBody>
}