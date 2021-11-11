package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.ArtificialProjectR020Request
import com.yxf.vehicleinspection.bean.request.ImageItemR017Request
import com.yxf.vehicleinspection.bean.request.InspectionPhotoW007Request
import com.yxf.vehicleinspection.bean.response.*
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.WriteService
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
class InspectionItemRepository {
    fun getImageItemData(
        Lsh: String,
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
    ): LiveData<List<ImageItemR017Response>> {
        val liveData = MutableLiveData<List<ImageItemR017Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_IMAGE_ITEM,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(ImageItemR017Request(Lsh, Jyxm, Ajywlb, Hjywlb))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val imageItemList = ArrayList<ImageItemR017Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            imageItemList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, ImageItemR017Response::class.java))
                        }
                        liveData.value = imageItemList
                    } else {
                        Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun getSelectItemData(
        Lsh: String,
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
    ): LiveData<ArtificialProjectR020Response> {
        val liveData = MutableLiveData<ArtificialProjectR020Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_ARTIFICIAL_PROJECT,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(ArtificialProjectR020Request(Lsh, Jyxm, Ajywlb, Hjywlb))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        val artificialProjectList = ArrayList<ArtificialProjectR020Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            artificialProjectList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, ArtificialProjectR020Response::class.java))
                        }
                        if (artificialProjectList.isNotEmpty()){
                            liveData.value = artificialProjectList[0]
                        }

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

    fun postInspectionPhotoW007(list: List<InspectionPhotoW007Request>): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call =
            RetrofitService.create(WriteService::class.java).write(ApiStatic.WRITE_INSPECTION_PHOTO,
                IpHelper.getIpAddress(),
                JsonDataHelper.getJsonData(list))
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1") {
                        liveData.value = true
                    } else {
                        liveData.value = false
                        if (commonResponse.Message != null) {
                            Toast.makeText(MyApp.context,
                                commonResponse.Message,
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    liveData.value = false
                    Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
}