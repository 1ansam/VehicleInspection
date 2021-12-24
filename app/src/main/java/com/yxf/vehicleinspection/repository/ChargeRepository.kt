package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.BuyerParamsR026Response
import com.yxf.vehicleinspection.bean.response.ChargeStatusR014Response
import com.yxf.vehicleinspection.bean.response.InvoiceParamsR025Response
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
    fun getChargeStatus(oid : String, ajlsh : String) : LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_CHARGE_STATUS,
            getIpAddress(),
            getJsonData(ChargeStatusR014Request(oid,ajlsh))
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

    fun postChargePayment(saveChargeInfoW004Request: SaveChargeInfoW004Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_CHARGE_INFO,
            getIpAddress(),
            getJsonData(saveChargeInfoW004Request)
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

    fun getInvoiceParams() : LiveData<InvoiceParamsR025Response>{
        val liveData = MutableLiveData<InvoiceParamsR025Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_INVOICE_PARAMS,
            getIpAddress(),
            getJsonData(InvoiceParamsR025Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun postInvoice(saveInvoiceW005Request: SaveInvoiceW005Request) : LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_INVOICE_INFO,
            getIpAddress(),
            getJsonData(saveInvoiceW005Request)
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

    fun getBuyerParams(buyerParamsR026Request: BuyerParamsR026Request) : LiveData<BuyerParamsR026Response>{
        val liveData = MutableLiveData<BuyerParamsR026Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_BUYER_PARAMS,
            getIpAddress(),
            getJsonData(buyerParamsR026Request)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun getChargeInfo(ajlsh: String): LiveData<SaveChargeInfoW004Request> {
        val liveData = MutableLiveData<SaveChargeInfoW004Request>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_CHARGE_STATUS,
            getIpAddress(),
            getJsonData(ChargeStatusR014Request("",ajlsh))
        )
        call.enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
        return liveData
    }


}