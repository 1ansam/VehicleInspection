package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.request.VehicleQueueRequset
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.room.JsCsCodeDao
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.IpHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/9/29
 */
class PersonInspectionRepository(private val jsCsCodeDao: JsCsCodeDao) {
    fun getData(hphm : String) : MutableLiveData<ArrayList<VehicleQueueResponse>>{
        val liveData = MutableLiveData<ArrayList<VehicleQueueResponse>>()

        val dataService = RetrofitService.create(QueryService::class.java)
        val vehicleQueueArray = ArrayList<VehicleQueueRequset>()
        vehicleQueueArray.add(VehicleQueueRequset(hphm))
        val commonRequestString = GsonSingleton.getGson().toJson(CommonRequest(vehicleQueueArray))
        val call = dataService.query("LYYDJKR002",IpHelper.getIpAddress(),commonRequestString)
        call.enqueue(object  : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //.string只允许调用一次
                    val stringResponse = response.body()?.string()
                    val queryResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (queryResponse.Code.equals("1")) {
                                val userInfoList = ArrayList<VehicleQueueResponse>()
                                for (index in 0 until queryResponse.Body?.size!!) {
                                    val bodyJson =
                                        GsonSingleton.getGson().toJson(queryResponse.Body[index])
                                    userInfoList.add(GsonSingleton.getGson()
                                        .fromJson(bodyJson, VehicleQueueResponse::class.java))
                                }
                        liveData.value = userInfoList


                    } else {
                        Toast.makeText(MyApp.context,
                            queryResponse.Message,
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(MyApp.context,
                        response.message(),
                        Toast.LENGTH_LONG).show()
                }
//                if (response.body()?.code.equals("1")){
//                    var modelList = ArrayList<Data>()
//                    val dataList = response.body()!!.data
//                    for (index in 0 until dataList.size){
//                        if (response.body()!!.data[index].hphm.contains(hphm)){
//                            modelList.add(response.body()!!.data[index])
//                        }
//                    }
//                    liveData.value = modelList
//                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context,"${t.message}",Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
}