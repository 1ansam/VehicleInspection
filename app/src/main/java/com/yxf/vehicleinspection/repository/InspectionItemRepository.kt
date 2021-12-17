package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.*
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.UploadFile
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

/**
 *   author:yxf
 *   time:2021/11/4
 */
class InspectionItemRepository {
    fun getUserInfo(): LiveData<List<UserInfoR001Response>> {
        val liveData = MutableLiveData<List<UserInfoR001Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_ALL_USER,
            getIpAddress(),
            getJsonData(AllUserInfoR001Request())
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
    fun getImageItemData(
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
        Ajlsh : String,
        Hjlsh : String,
    ): LiveData<List<ImageItemR017Response>> {
        val liveData = MutableLiveData<List<ImageItemR017Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_IMAGE_ITEM,
            getIpAddress(),
            getJsonData(ImageItemR017Request(Jyxm, Ajywlb, Hjywlb,Ajlsh, Hjlsh))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response,liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun getSelectItemData(
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
        Ajlsh : String,
        Hjlsh : String,
    ): LiveData<List<ArtificialProjectR020Response>> {
        val liveData = MutableLiveData<List<ArtificialProjectR020Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_ARTIFICIAL_PROJECT,
            getIpAddress(),
            getJsonData(ArtificialProjectR020Request(Jyxm, Ajywlb, Hjywlb,Ajlsh, Hjlsh))
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
    fun getNetworkQueryInfoName(): List<String>{
        return listOf("联网查询结果描述","机动车所有人","手机号码","联系地址","邮政编码")
    }


    fun postInspectionPhotoW007(list: List<InspectionPhotoW007Request>): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call =
            RetrofitService.create(WriteService::class.java).write(
                WRITE_INSPECTION_PHOTO,
                getIpAddress(),
                getJsonData(list))
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    fun <T> postArtificialProjectW011(list : List<ArtificialProjectW011Request<T>>) : MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_ARTIFICIAL_PROJECT,
            getIpAddress(),
            getJsonData(list)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    fun postSaveVideoW008(saveVideoW008Request: SaveVideoW008Request): LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_VIDEO,
            getIpAddress(),
            getJsonData(saveVideoW008Request)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    fun postProjectStartW010(projectStartW010Request: ProjectStartW010Request):LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_PROJECT_START,
            getIpAddress(),
            getJsonData(projectStartW010Request)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    fun postProjectEndW012(projectEndW012Request: ProjectEndW012Request): LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_PROJECT_END,
            getIpAddress(),
            getJsonData(projectEndW012Request)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun postUploadFile(file : File, requestBody: RequestBody):LiveData<String>{
        val liveData = MutableLiveData<String>()
        val call = RetrofitService.create(UploadFile::class.java).upload(
            uploadFile("objFile",file,requestBody)
        )
        call.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    liveData.value = response.body()?.string()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    return liveData
    }

    fun postTakePhoto(takePhotoW009Request: TakePhotoW009Request) : LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_TAKE_PHOTO,
            getIpAddress(),
            getJsonData(takePhotoW009Request)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>){
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun getLeastestTime(ajcx : String, jyxm : String): MutableLiveData<LeastestTimeR019Response> {
        val liveData = MutableLiveData<LeastestTimeR019Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_LEASTEST_TIME,
            getIpAddress(),
            getJsonData(LeastestTimeR019Request(ajcx,jyxm))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
}