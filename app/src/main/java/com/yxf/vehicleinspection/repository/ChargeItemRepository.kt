package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ChargeItemR004Request
import com.yxf.vehicleinspection.bean.response.ChargeItemR004Response
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.room.ChargeItemDao
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.QUERY_CHARGE_LIST
import com.yxf.vehicleinspection.utils.getIpAddress
import com.yxf.vehicleinspection.utils.getJsonData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/12/21
 */
class ChargeItemRepository(private val dao : ChargeItemDao) {
    //数据行数初始化为0
    //从服务器获取数据时重新赋值
    //为与插入数据做比对
    var rowNum = 0

    /**
     * 从服务器获取收费条目
     */
    fun getChargeItem() : Pair<LiveData<List<ChargeItemR004Response>>,LiveData<String>>{
        val liveData = MutableLiveData<List<ChargeItemR004Response>>()
        val message = MutableLiveData<String>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_CHARGE_LIST,
            getIpAddress(),
            getJsonData(ChargeItemR004Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.instance
                        .fromJson(stringResponse, CommonResponse::class.java)
                    rowNum = commonResponse.RowNum
                    if (commonResponse.Code == "1"){
                        val beanList = ArrayList<ChargeItemR004Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.instance.toJson(element)
                            beanList.add(
                                GsonSingleton.instance
                                .fromJson(bodyJson, ChargeItemR004Response::class.java))
                        }
                        liveData.value = beanList
                    }else{
                        if (commonResponse.Code == null){
                            message.value = "服务器Code=Null"
                            Toast.makeText(MyApp.context, "服务器Code=Null", Toast.LENGTH_SHORT).show()
                        }else{
                            message.value = commonResponse.Message
                            Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    message.value = response.message()
                    Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                message.value = t.message
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return Pair(liveData,message)
    }

    /**
     * 从数据库获取收费条目
     */
    fun getChargeItemFromDb(): LiveData<List<ChargeItemR004Response>> {
        return dao.getChargeItemFromDb()
    }

    suspend fun insertChargeItem(chargeItemR004ResponseList: List<ChargeItemR004Response>): List<Long> {
        return dao.insertChargeItem(chargeItemR004ResponseList)
    }

    suspend fun updateChargeItem(chargeItemR004ResponseList: List<ChargeItemR004Response>){
        return dao.updateChargeItem(chargeItemR004ResponseList)
    }

    suspend fun deleteChargeItem(){
        return dao.deleteChargeItem()
    }
}