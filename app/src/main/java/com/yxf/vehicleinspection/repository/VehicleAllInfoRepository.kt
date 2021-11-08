package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.VehicleAllInfo005Request
import com.yxf.vehicleinspection.bean.response.VehicleAllInfo005Response
import com.yxf.vehicleinspection.bean.response.CommonResponse
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
class VehicleAllInfoRepository {
    /**
     *   @param Lsh 流水号 VARCHAR(20) 按流水号查询时其他参数可空（空字符串）
     *   @param Hphm 号牌号码 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Hpzl 号牌种类 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Clsbdh 车辆识别代号 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Xszbh 行驶证编号 按行驶证编号查询时其他参数可空（空字符串）
     */
    fun getVehicleAllInfoRepository(Lsh : String, Hphm : String, Hpzl : String, Clsbdh : String, Xszbh : String) : LiveData<List<VehicleAllInfo005Response>>{
        val liveData = MutableLiveData<List<VehicleAllInfo005Response>>()
        val call = RetrofitService.create(QueryService::class.java)
            .query(ApiStatic.QUERY_VEHICLE_ALL_INFO,IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(VehicleAllInfo005Request(Lsh, Hphm, Hpzl, Clsbdh, Xszbh)))
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse,CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")){
                        val vehicleAllInfoList = ArrayList<VehicleAllInfo005Response>()
                        for (element in commonResponse.Body){
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            vehicleAllInfoList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson,VehicleAllInfo005Response::class.java))
                        }
                        liveData.value = vehicleAllInfoList
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