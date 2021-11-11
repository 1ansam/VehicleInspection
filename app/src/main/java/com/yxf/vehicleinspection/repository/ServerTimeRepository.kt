package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ServerTimeR011Request
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.bean.response.ServerTimeR011Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.ApiStatic
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.IpHelper
import com.yxf.vehicleinspection.utils.JsonDataHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/11/11
 */
class ServerTimeRepository {
    fun getServerTime() : LiveData<ServerTimeR011Response>{
        val liveData = MutableLiveData<ServerTimeR011Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_SERVER_TIME,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(ServerTimeR011Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        val serverTimeList = ArrayList<ServerTimeR011Response>()
                        for (element in commonResponse.Body){
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            serverTimeList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, ServerTimeR011Response::class.java))
                        }
                        if (serverTimeList.isNotEmpty()){
                            liveData.value = serverTimeList[0]
                        }
                    }else{
                        if (commonResponse.Message!=null){
                            Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
}