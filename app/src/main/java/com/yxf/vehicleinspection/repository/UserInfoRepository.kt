package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.AllUserInfoRequest
import com.yxf.vehicleinspection.bean.request.UserInfoRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.UserInfoResponse
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
 *   time:2021/10/12
 */
class UserInfoRepository {

    fun getUserInfo(): LiveData<List<UserInfoResponse>> {
        val liveData = MutableLiveData<List<UserInfoResponse>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_ALL_USER,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(AllUserInfoRequest())
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //.string只允许调用一次

                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {
                        val userInfoResponse = ArrayList<UserInfoResponse>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.getGson().toJson(element)
                            userInfoResponse.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, UserInfoResponse::class.java))
                        }
                        liveData.value = userInfoResponse


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
                Toast.makeText(MyApp.context,
                    t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }

    fun getUserLogin(username: String, password: String): LiveData<Boolean> {
        val isLoading = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            ApiStatic.WRITE_USER_LOGIN,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(UserInfoRequest(username, password, IpHelper.getIpAddress()))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            isLoading.value = false
                if (response.isSuccessful) {
                    //.string只允许调用一次

                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code.equals("1")) {

                        val userInfoResponse = ArrayList<UserInfoResponse>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.getGson().toJson(element)
                            userInfoResponse.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, UserInfoResponse::class.java))
                        }

                        isLoading.value = true
                        Toast.makeText(MyApp.context,
                            commonResponse.Message,
                            Toast.LENGTH_LONG).show()

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
                isLoading.value = false
                Toast.makeText(MyApp.context, t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
        return isLoading
    }
}