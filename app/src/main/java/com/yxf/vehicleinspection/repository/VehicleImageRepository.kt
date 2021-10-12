package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.VehicleImageRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.VehicleImageResponse
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
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
 *   time:2021/10/12
 */
class VehicleImageRepository {
    /**
     * @param Lsh 流水号
     */
    fun getImageData(Lsh : String) : LiveData<List<VehicleImageResponse>> {
        val liveData = MutableLiveData<List<VehicleImageResponse>>()
        val call = RetrofitService.create(QueryService::class.java)
            .query(ApiStatic.QUERY_VEHICLE_IMAGE,IpHelper.getIpAddress(),
                JsonDataHelper.getJsonData(VehicleImageRequest(Lsh)))
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //.string只允许调用一次
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val vehicleImageList = ArrayList<VehicleImageResponse>()
                        for (index in 0 until commonResponse.Body.size) {
                            val bodyJson =
                                GsonSingleton.getGson().toJson(commonResponse.Body[index])
                            vehicleImageList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, VehicleImageResponse::class.java))
                        }
                        liveData.value = vehicleImageList


                    } else {
                        Toast.makeText(MyApp.context,
                            commonResponse.Message,
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(MyApp.context,
                        response.message(),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
}