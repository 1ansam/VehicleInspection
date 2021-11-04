package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ImageItemRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.ImageItemResponse
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoResponse
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
class ImageItemRepository {
    fun getImageItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String) : LiveData<List<ImageItemResponse>>{
        val liveData = MutableLiveData<List<ImageItemResponse>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_IMAGE_ITEM,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(ImageItemRequest(Lsh, Jyxm, Ajywlb, Hjywlb))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val imageItemList = ArrayList<ImageItemResponse>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            imageItemList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, ImageItemResponse::class.java))
                        }
                        liveData.value = imageItemList
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