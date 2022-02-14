package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ServerTimeR011Request
import com.yxf.vehicleinspection.bean.response.ServerTimeR011Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.QUERY_SERVER_TIME
import com.yxf.vehicleinspection.utils.getIpAddress
import com.yxf.vehicleinspection.utils.getJsonData
import com.yxf.vehicleinspection.utils.response2Bean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/11/11
 */
class ServerTimeRepository {
    /**
     * 获取服务器时间
     */
    fun getServerTime() : LiveData<ServerTimeR011Response>{
        val liveData = MutableLiveData<ServerTimeR011Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_SERVER_TIME,
            getIpAddress(),
            getJsonData(ServerTimeR011Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
}