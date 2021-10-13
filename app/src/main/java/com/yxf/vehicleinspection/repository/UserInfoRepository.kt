package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.AllUserInfoRequest
import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.request.UserInfoRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.UserInfoResponse
import com.yxf.vehicleinspection.bean.response.VehicleImageResponse
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
                        for (index in 0 until commonResponse.Body.size) {
                            val bodyJson =
                                GsonSingleton.getGson().toJson(commonResponse.Body[index])
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
        var isLogin = MutableLiveData<Boolean>()
        isLogin.value = false
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_ALL_USER,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(UserInfoRequest(username, password, IpHelper.getIpAddress()))
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
                        for (index in 0 until commonResponse.Body.size) {
                            val bodyJson =
                                GsonSingleton.getGson().toJson(commonResponse.Body[index])
                            userInfoResponse.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, UserInfoResponse::class.java))
                        }
                        isLogin.value = true
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
                Toast.makeText(MyApp.context, t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
        return isLogin
    }
}