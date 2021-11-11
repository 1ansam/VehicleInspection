package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.DataDictionaryR003Request
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
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
    suspend fun insertDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>) {
        dao.insertDataDictionary(dataDictionaryListResponse)
    }
    suspend fun updateDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>){
        dao.updateDataDictionary(dataDictionaryListResponse)
    }
    fun getMc(Fl : String,Dm : String): LiveData<String> {
        return dao.getMc(Fl, Dm)
    }

    fun getMcList(Fl : String) : LiveData<List<String>>{
        return dao.getMcList(Fl)
    }
    fun getDM(Fl : String, FlMc : String): LiveData<String> {
        return dao.getDM(Fl, FlMc)
    }
    fun getListFromFl(Fl: String) : LiveData<List<DataDictionaryR003Response>>{
        return dao.getListFromFl(Fl)
    }
    fun getDataDictionaryExist() : LiveData<DataDictionaryR003Response>{
        return dao.getDataDictionaryExist()
    }
    fun getDictionaryData() : LiveData<List<DataDictionaryR003Response>>{
        val liveData = MutableLiveData<List<DataDictionaryR003Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            ApiStatic.QUERY_DATA_DICTIONARY,
            IpHelper.getIpAddress(),
            JsonDataHelper.getJsonData(DataDictionaryR003Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    val stringResponse = response.body()?.string()
                    val commonResponse = GsonSingleton.getGson()
                        .fromJson(stringResponse, CommonResponse::class.java)
                    if (commonResponse.Code == "1"){
                        val dataDictionaryList = ArrayList<DataDictionaryR003Response>()
                        for (element in commonResponse.Body) {
                            val bodyJson = GsonSingleton.getGson().toJson(element)
                            dataDictionaryList.add(GsonSingleton.getGson()
                                .fromJson(bodyJson, DataDictionaryR003Response::class.java))
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