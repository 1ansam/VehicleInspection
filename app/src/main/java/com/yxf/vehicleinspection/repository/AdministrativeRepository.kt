package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.AdministrativeR023Request
import com.yxf.vehicleinspection.bean.response.AdministrativeR023Response
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.room.AdministrativeDao
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.QUERY_ADMINISTRATIVE
import com.yxf.vehicleinspection.utils.getIpAddress
import com.yxf.vehicleinspection.utils.getJsonData
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/12/14
 */
class AdministrativeRepository(private val dao : AdministrativeDao) {
    //数据行数初始化为0
    //从服务器获取数据时重新赋值
    //为与插入数据做比对
    var rowNum = 0
    suspend fun insertAdministrativeList(administrativeList: List<AdministrativeR023Response>): List<Long> {
        return dao.insertAdministrativeList(administrativeList)
    }

    suspend fun deleteAdministrative(){
        return dao.deleteAdministrative()
    }

    /**
     * 从服务器获取行政区划数据
     */
    fun getAdministrativeList() : LiveData<List<AdministrativeR023Response>>{
        val liveData = MutableLiveData<List<AdministrativeR023Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_ADMINISTRATIVE,
            getIpAddress(),
            getJsonData(AdministrativeR023Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.instance
                        .fromJson(stringResponse, CommonResponse::class.java)
                    rowNum = 0.takeIf{commonResponse.RowNum == null}?:commonResponse.RowNum
                    if (commonResponse.Code == "1"){
                        val beanList = ArrayList<AdministrativeR023Response>()

                        commonResponse.Body.forEach {
                            val bodyJson =
                                GsonSingleton.instance.toJson(it)
                            beanList.add(
                                GsonSingleton.instance
                                    .fromJson(bodyJson, AdministrativeR023Response::class.java))
                        }

                        //使用forEach替代
//                        for (element in commonResponse.Body) {
//                            val bodyJson =
//                                GsonSingleton.instance.toJson(element)
//                            beanList.add(
//                                GsonSingleton.instance
//                                .fromJson(bodyJson, AdministrativeR023Response::class.java))
//                        }
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
     * 从数据库按名称获取行政区划
     * @param xzqhmc 行政区划名称
     */
    fun getAdministrativeListFromMc(xzqhmc : String) : LiveData<List<AdministrativeR023Response>>{
        return dao.getAdministrativeListFromMc(xzqhmc)
    }
}