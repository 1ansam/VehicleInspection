package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.VehicleInspectionItemRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemResponse
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
 *   time:2021/11/4
 */
class VehicleInspectionItemRepository {
    fun getVehicleInspectionItem(Lsh : String): LiveData<List<VehicleInspectionItemResponse>> {
        val liveData = MutableLiveData<List<VehicleInspectionItemResponse>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_VEHICLE_INSPECTION_ITEM,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(VehicleInspectionItemRequest(Lsh))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val vehicleInspectionItemList = ArrayList<VehicleInspectionItemResponse>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            vehicleInspectionItemList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, VehicleInspectionItemResponse::class.java))
                        }
                        liveData.value = vehicleInspectionItemList
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

}