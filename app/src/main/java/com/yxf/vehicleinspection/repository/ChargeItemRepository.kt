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
    var rowNum = 0
    fun getChargeItem() : LiveData<List<ChargeItemR004Response>>{
        val liveData = MutableLiveData<List<ChargeItemR004Response>>()
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