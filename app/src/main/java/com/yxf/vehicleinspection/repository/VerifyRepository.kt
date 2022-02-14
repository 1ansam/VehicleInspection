package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ModerationQueueR013Request
import com.yxf.vehicleinspection.bean.request.VehicleImageR007Request
import com.yxf.vehicleinspection.bean.request.VehicleVideoR008Request
import com.yxf.vehicleinspection.bean.response.ModerationQueueR013Response
import com.yxf.vehicleinspection.bean.response.VehicleImageR007Response
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/12/17
 */
class VerifyRepository {
    /**
     * 获取待审核队列
     * @param hphm 号牌号码
     * @param shyw 审核业务（"0"=同检 "1"=安检 "2"=环检）
     */
    fun getVerifyDataQueue(hphm: String, shyw : String): LiveData<List<ModerationQueueR013Response>> {
        val liveData = MutableLiveData<List<ModerationQueueR013Response>>()
        val dataService = RetrofitService.create(QueryService::class.java)
        val call = dataService.query(
            QUERY_VERIFY_QUEUE,
            getIpAddress(),
            getJsonData(ModerationQueueR013Request(hphm,shyw))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
    /**
     * 获取机动车检验照片
     * @param ajLsh 安检流水号
     * @param hjLsh 环检流水号
     */
    fun getVehicleImage(ajLsh : String, hjLsh : String) : LiveData<List<VehicleImageR007Response>> {
        val liveData = MutableLiveData<List<VehicleImageR007Response>>()
        val call = RetrofitService.create(QueryService::class.java)
            .query(
                QUERY_VEHICLE_IMAGE,
                getIpAddress(),
                getJsonData(VehicleImageR007Request(ajLsh, hjLsh)))
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response2ListBean(response, liveData)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
    /**
     * 获取机动车检验视频
     * @param ajLsh 安检流水号
     * @param hjLsh 环检流水号
     */
    fun getVehicleVideo(ajLsh : String, hjLsh : String) : LiveData<List<VehicleVideoR008Response>> {
        val liveData = MutableLiveData<List<VehicleVideoR008Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_VEHICLE_VIDEO,
            getIpAddress(),
            getJsonData(VehicleVideoR008Request(ajLsh, hjLsh ))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context,
                    t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
}