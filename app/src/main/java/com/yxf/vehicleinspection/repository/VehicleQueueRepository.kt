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
import com.yxf.vehicleinspection.utils.QUERY_VEHICLE_QUEUE
import com.yxf.vehicleinspection.utils.getIpAddress
import com.yxf.vehicleinspection.utils.getJsonData
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
    /**
     * 获取机动车待检队列
     * @param hphm 号牌号码
     */
    fun getInspectionQueue(hphm: String): LiveData<List<VehicleQueueR002Response>>{
        val liveData = MutableLiveData<List<VehicleQueueR002Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_VEHICLE_QUEUE,
            getIpAddress(),
            getJsonData(VehicleQueueR002Request(hphm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.instance
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        val beanList = ArrayList<VehicleQueueR002Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.instance.toJson(element)
                            val bean = GsonSingleton.instance
                                .fromJson(bodyJson, VehicleQueueR002Response::class.java)
                            if (bean.Sfsf == "1"){
                                beanList.add(bean)
                            }
                        }
                        liveData.value = beanList
                    }else{
                        if (commonResponse.Code == null){
                            Toast.makeText(MyApp.context, "Code=Null", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
    /**
     * 获取机动车未收费队列
     * @param hphm
     */
    fun getChargeQueue(hphm: String): LiveData<List<VehicleQueueR002Response>>{
        val liveData = MutableLiveData<List<VehicleQueueR002Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_VEHICLE_QUEUE,
            getIpAddress(),
            getJsonData(VehicleQueueR002Request(hphm))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response != null){
                    if (response.isSuccessful){
                        val stringResponse = response.body()?.string()
                        val commonResponse = GsonSingleton.instance
                            .fromJson(stringResponse, CommonResponse::class.java)
                        if (commonResponse.Code == "1"){
                            val beanList = ArrayList<VehicleQueueR002Response>()
                            for (element in commonResponse.Body) {
                                val bodyJson =
                                    GsonSingleton.instance.toJson(element)
                                val bean = GsonSingleton.instance
                                    .fromJson(bodyJson, VehicleQueueR002Response::class.java)
                                if (bean.Sfsf == "0"||(bean.Sfkp == "0")){
                                    beanList.add(bean)
                                }
                            }
                            liveData.value = beanList
                        }else{
                            if (commonResponse.Code == null){
                                Toast.makeText(MyApp.context, "Code=Null", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(MyApp.context, "response = null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }




}