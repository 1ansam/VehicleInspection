package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.VehicleQueueRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
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
 *   time:2021/9/29
 */
class VehicleQueueRepository {
    fun getInspectionDataQueue(hphm: String): LiveData<List<VehicleQueueResponse>> {

        val liveData = MutableLiveData<List<VehicleQueueResponse>>()
        val dataService = RetrofitService.create(QueryService::class.java)
        val call = dataService.query(
            ApiStatic.QUERY_VEHICLE_QUEUE,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(VehicleQueueRequest(hphm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //.string只允许调用一次
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val userInfoList = ArrayList<VehicleQueueResponse>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.getGson().toJson(element)
                            val userInfo = GsonSingleton.getGson().fromJson(bodyJson, VehicleQueueResponse::class.java)
                            if (!userInfo.Jyzt.equals("过程结束")){
                                userInfoList.add(userInfo)
                            }
                        }
                        liveData.value = userInfoList


                    } else {
                        Toast.makeText(MyApp.context,
                            commonResponse.Message,
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(MyApp.context,
                        response.message(),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
    fun getVerifyDataQueue(hphm: String): LiveData<List<VehicleQueueResponse>> {
        val liveData = MutableLiveData<List<VehicleQueueResponse>>()
        val dataService = RetrofitService.create(QueryService::class.java)
        val call = dataService.query(
            ApiStatic.QUERY_VEHICLE_QUEUE,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(VehicleQueueRequest(hphm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //.string只允许调用一次
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val userInfoList = ArrayList<VehicleQueueResponse>()
                        for (element in commonResponse.Body) {

                            val bodyJson =
                                GsonSingleton.getGson().toJson(element)
                            val userInfo = GsonSingleton.getGson().fromJson(bodyJson, VehicleQueueResponse::class.java)
                            if (userInfo.Jyzt.equals("过程结束")){
                                userInfoList.add(userInfo)
                            }
//                            userInfoList.add(GsonSingleton.getGson()
//                                .fromJson(bodyJson, VehicleQueueResponse::class.java))
                        }
                        liveData.value = userInfoList


                    } else {
                        Toast.makeText(MyApp.context,
                            commonResponse.Message,
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(MyApp.context,
                        response.message(),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }

}