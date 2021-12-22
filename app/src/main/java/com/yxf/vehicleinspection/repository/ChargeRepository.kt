package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ChargeDetail
import com.yxf.vehicleinspection.bean.request.ChargeOrder
import com.yxf.vehicleinspection.bean.request.ChargeStatusR014Request
import com.yxf.vehicleinspection.bean.request.SaveChargeInfoW004Request
import com.yxf.vehicleinspection.bean.response.ChargeStatusR014Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 *   author:yxf
 *   time:2021/12/22
 */
class ChargeRepository {
    fun getChargeStatus(oid : String) : LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_CHARGE_STATUS,
            getIpAddress(),
            getJsonData(ChargeStatusR014Request(oid))
        )
        call.enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
        return liveData
    }

    fun postChargePayment(chargeOrder: ChargeOrder,chargeDetails : List<ChargeDetail>): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_CHARGE_INFO,
            getIpAddress(),
            getJsonData(SaveChargeInfoW004Request(chargeOrder, chargeDetails))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
}