package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.compose.runtime.internal.updateLiveLiteralValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.AllUserInfoR001Request
import com.yxf.vehicleinspection.bean.request.UserInfoRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/10/12
 */
class UserInfoRepository {
    val isLogin = MutableLiveData<Boolean>()


    fun getUserLogin(username: String, password: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_USER_LOGIN,
            getIpAddress(),
            getJsonData(UserInfoRequest(username, password, getIpAddress()))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response,liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }

    fun getUser(username: String, password: String) : LiveData<UserInfoR001Response>{
        val liveData = MutableLiveData<UserInfoR001Response>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_USER_LOGIN,
            getIpAddress(),
            getJsonData(UserInfoRequest(username, password, getIpAddress()))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.instance
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        isLogin.value = true
                        val beanList = ArrayList<UserInfoR001Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson =
                                GsonSingleton.instance.toJson(element)
                            beanList.add(GsonSingleton.instance
                                .fromJson(bodyJson, UserInfoR001Response::class.java))
                        }
                        if (beanList.isNotEmpty()){
                            liveData.value = beanList[0]
                        }
                    }else{
                        isLogin.value = false
                        if (commonResponse.Code == null){
                            Toast.makeText(MyApp.context, "服务器Code=Null", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    isLogin.value = false
                    Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                isLogin.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
}