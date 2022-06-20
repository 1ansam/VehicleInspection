package com.yxf.vehicleinspection.service

import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *   author:yxf
 *   time:2021/10/11
 *   retrofit公共查询请求接口
 */
interface QueryService {
    /**
     * @param jkId 接口编码，例“LYYDJKW001”
     * @param zdbs 终端标识（ip地址）
     * @param jsonData json数据（见接口文档）
     * @return 返回通用响应体
     */
    @POST("VehicleInspection/Query")
    fun  query(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String,
    ): Call<ResponseBody>


    //coroutines
    @POST("VehicleInspection/Query")
    suspend fun querySuspend(
        @Query("jkId") jkId: String,
        @Query("zdbs") zdbs: String,
        @Query("jsonData") jsonData: String
    ): Response<CommonResponse<VehicleQueueR002Response>>
}