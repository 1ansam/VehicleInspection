package com.yxf.vehicleinspection.service

import com.yxf.vehicleinspection.bean.DataJson
import retrofit2.Call
import retrofit2.http.GET

interface DataService {
    @GET("getData.json")
    fun getData() : Call<DataJson>
}