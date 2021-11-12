package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.SystemParamsR015Request
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response
import com.yxf.vehicleinspection.room.SystemParamsDao
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
class SystemParamsRepository(private val systemParamsDao: SystemParamsDao) {
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR015Response>){
        systemParamsDao.insertSystemParams(systemParamsList)
    }
    suspend fun updateSystemParams(systemParamsList: List<SystemParamsR015Response>){
        systemParamsDao.updateSystemParams(systemParamsList)
    }
    suspend fun deleteSystemParams(){
        systemParamsDao.deleteSystemParams()
    }
    fun getSystemParamsData() : LiveData<List<SystemParamsR015Response>> {
        val liveData = MutableLiveData<List<SystemParamsR015Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_SYSTEM_PARAMS,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(SystemParamsR015Request())
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        val systemParamsList = ArrayList<SystemParamsR015Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            systemParamsList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, SystemParamsR015Response::class.java))
                        }
                        liveData.value = systemParamsList
                    }else{
                        Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
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

    fun getJyjgbh(Sjlb : String) : LiveData<String>{
        return systemParamsDao.getJyjgbh(Sjlb)
    }
    fun getWebPass(Sjlb : String): LiveData<String> {
        return systemParamsDao.getWebPass(Sjlb)
    }
    fun getSystemParamsDataExist() : LiveData<SystemParamsR015Response> {
        return systemParamsDao.getSystemParamsExist()
    }
}