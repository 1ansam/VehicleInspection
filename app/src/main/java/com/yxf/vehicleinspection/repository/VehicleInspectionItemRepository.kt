package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.VehicleInspectionItemR006Request
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemR006Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.QUERY_VEHICLE_INSPECTION_ITEM
import com.yxf.vehicleinspection.utils.getIpAddress
import com.yxf.vehicleinspection.utils.getJsonData
import com.yxf.vehicleinspection.utils.response2ListBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/11/4
 */
class VehicleInspectionItemRepository {
    /**
     * 获取机动车检验项目
     * @param Ajlsh 安检流水号
     * @param Hjlsh 环检流水号
     * @param Ajywlb 安检业务类别
     * @param Hjywlb 胡安检业务类别
     */
    fun getVehicleInspectionItem(Ajlsh : String, Hjlsh : String, Ajywlb : String, Hjywlb : String): LiveData<List<VehicleInspectionItemR006Response>> {
        val liveData = MutableLiveData<List<VehicleInspectionItemR006Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_VEHICLE_INSPECTION_ITEM,
            getIpAddress(),
            getJsonData(VehicleInspectionItemR006Request(Ajlsh, Hjlsh, Ajywlb, Hjywlb))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

}