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
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/11/11
 */
class SystemParamsRepository(private val systemParamsDao: SystemParamsDao) {
    var rowNum = 0
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR015Response>) : List<Long>{
        return systemParamsDao.insertSystemParams(systemParamsList)
    }
    suspend fun updateSystemParams(systemParamsList: List<SystemParamsR015Response>){
        systemParamsDao.updateSystemParams(systemParamsList)
    }
    suspend fun deleteSystemParams(){
        systemParamsDao.deleteSystemParams()
    }
    /**
     * 获取系统参数
     */
    fun getSystemParamsData() : LiveData<List<SystemParamsR015Response>> {
        val liveData = MutableLiveData<List<SystemParamsR015Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_SYSTEM_PARAMS,
            getIpAddress(),
            getJsonData(SystemParamsR015Request())
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.instance
                        .fromJson(stringResponse, CommonResponse::class.java)
                    rowNum = 0.takeIf{commonResponse.RowNum == null}?:commonResponse.RowNum
                    if (commonResponse.Code == "1"){
                        val beanList = ArrayList<SystemParamsR015Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.instance.toJson(element)
                            beanList.add(GsonSingleton.instance
                                .fromJson(bodyJson, SystemParamsR015Response::class.java))
                        }
                        liveData.value = beanList
                    }else{
                        if (commonResponse.Code == null){
                            Toast.makeText(MyApp.context, "服务器Code=Null", Toast.LENGTH_SHORT).show()
                        }else{
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
    /**
     * 从数据库获取系统参数
     */
    fun getSystemParamsFromDb() : LiveData<List<SystemParamsR015Response>> {
        return systemParamsDao.getSystemParamsFromDb()
    }
    /**
     * 从数据库获取系统参数
     * @param Sjlb 数据类别（AJ或Hj）
     */
    fun getSystemParamsFromDb(Sjlb: String) : LiveData<SystemParamsR015Response> {
        return systemParamsDao.getSystemParamsFromDb(Sjlb)
    }
    /**
     * 获取检验机构编号
     * @param Sjlb 数据类别（AJ）默认为AJ
     */
    fun getJyjgbh(Sjlb : String = "AJ") : LiveData<String>{
        return systemParamsDao.getJyjgbh(Sjlb)
    }
    /**
     * 获取检验业务授权码
     * @param Sjlb 数据类别（AJ）默认为AJ
     */
    fun getWebPass(Sjlb : String = "AJ"): LiveData<String> {
        return systemParamsDao.getWebPass(Sjlb)
    }

    /**
     * 获取流水号首字母
     */
    fun getLshSzm() : LiveData<String>{
        return systemParamsDao.getLshSzm()
    }
    fun getSystemParamsDataExist() : LiveData<SystemParamsR015Response> {
        return systemParamsDao.getSystemParamsExist()
    }
}