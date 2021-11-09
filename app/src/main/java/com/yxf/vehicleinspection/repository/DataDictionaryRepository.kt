package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.DataDictionaryRequest003
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.DataDictionaryResponse003
import com.yxf.vehicleinspection.bean.response.ImageItemResponse
import com.yxf.vehicleinspection.room.DataDictionaryDao
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
 *   time:2021/11/9
 */
class DataDictionaryRepository(private val dao : DataDictionaryDao) {
    suspend fun insertData(dataDictionaryList: List<DataDictionaryResponse003>) {
        dao.insertJsCsCode(dataDictionaryList)
    }
    suspend fun updateData(dataDictionaryList: List<DataDictionaryResponse003>){
        dao.updateJsCsCode(dataDictionaryList)
    }
    fun getMc(Fl : String,Dm : String): LiveData<String> {
        return dao.getMc(Fl, Dm)
    }
    fun getDM(Fl : String, FlMc : String): LiveData<String> {
        return dao.getDM(Fl, FlMc)
    }
    fun getDataExist() : LiveData<DataDictionaryResponse003>{
        return dao.getJsCsCodeExist()
    }
    fun getDictionaryData() : LiveData<List<DataDictionaryResponse003>>{
        val liveData = MutableLiveData<List<DataDictionaryResponse003>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_DATA_DICTIONARY,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(DataDictionaryRequest003())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        val dataDictionaryList = ArrayList<DataDictionaryResponse003>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            dataDictionaryList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, DataDictionaryResponse003::class.java))
                        }
                        liveData.value = dataDictionaryList
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