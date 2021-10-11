package com.yxf.vehicleinspection.repository

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.Data
import com.yxf.vehicleinspection.bean.QueryResponse
import com.yxf.vehicleinspection.bean.VehicleQueue
import com.yxf.vehicleinspection.room.JsCsCodeDao
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.IpHelper
import com.yxf.vehicleinspection.view.activity.HomeActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/9/29
 */
class PersonInspectionRepository(private val jsCsCodeDao: JsCsCodeDao) {
    fun getData(hphm : String) : MutableLiveData<ArrayList<VehicleQueue>>{
        val liveData = MutableLiveData<ArrayList<VehicleQueue>>()

        val dataService = RetrofitService.create(QueryService::class.java)
        val call = dataService.query("LYYDJKR002",IpHelper.getIpAddress(),"{}")
        call.enqueue(object  : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //.string只允许调用一次
                    val stringResponse = response.body()?.string()
                    val queryResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, QueryResponse::class.java)
                    if (queryResponse.Code.equals("1")) {
                                val userInfoList = ArrayList<VehicleQueue>()
                                for (index in 0 until queryResponse.Body?.size!!) {
                                    val bodyJson =
                                        GsonSingleton.getGson().toJson(queryResponse.Body[index])
                                    userInfoList.add(GsonSingleton.getGson()
                                        .fromJson(bodyJson, VehicleQueue::class.java))
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