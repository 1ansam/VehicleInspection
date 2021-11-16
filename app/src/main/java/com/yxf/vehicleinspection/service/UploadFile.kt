package com.yxf.vehicleinspection.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 *   author:yxf
 *   time:2021/11/16
 */
interface UploadFile{
    @Multipart
    @POST("/VehicleInspection/UploadFile")
    fun upload(@Part file : MultipartBody.Part) : Call<ResponseBody>
}