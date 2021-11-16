package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.VehicleQueueR002Request
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/9/29
 *   机动车队列仓库
 */
class VehicleQueueRepository {
    fun getInspectionDataQueue(hphm: String): LiveData<List<VehicleQueueR002Response>> {

        val liveData = MutableLiveData<List<VehicleQueueR002Response>>()
        val dataService = RetrofitService.create(QueryService::class.java)
        val call = dataService.query(
            QUERY_VEHICLE_QUEUE,
            getIpAddress(),
            getJsonData(VehicleQueueR002Request(hphm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
    fun getVerifyDataQueue(hphm: String): LiveData<List<VehicleQueueR002Response>> {
        val liveData = MutableLiveData<List<VehicleQueueR002Response>>()
        val dataService = RetrofitService.create(QueryService::class.java)
        val call = dataService.query(
            QUERY_VEHICLE_QUEUE,
            getIpAddress(),
            getJsonData(VehicleQueueR002Request(hphm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }

}