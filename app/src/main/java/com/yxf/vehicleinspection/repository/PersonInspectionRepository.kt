package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.Data
import com.yxf.vehicleinspection.bean.DataJson
import com.yxf.vehicleinspection.room.JsCsCodeDao
import com.yxf.vehicleinspection.service.DataService
import com.yxf.vehicleinspection.singleton.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/9/29
 */
class PersonInspectionRepository(private val jsCsCodeDao: JsCsCodeDao) {
    fun getData(hphm : String) : MutableLiveData<ArrayList<Data>>{
        val liveData = MutableLiveData<ArrayList<Data>>()

        val dataService = RetrofitService.create(DataService::class.java)
        val call = dataService.getData()
        call.enqueue(object  : Callback<DataJson>{
            override fun onResponse(call: Call<DataJson>, response: Response<DataJson>) {
                if (response.body()?.code.equals("1")){
                    var modelList = ArrayList<Data>()
                    val dataList = response.body()!!.data
                    for (index in 0 until dataList.size){
                        if (response.body()!!.data[index].hphm.contains(hphm)){
                            modelList.add(response.body()!!.data[index])
                        }
                    }
                    liveData.value = modelList
                }
            }
            override fun onFailure(call: Call<DataJson>, t: Throwable) {
                Toast.makeText(MyApp.context,"${t.message}",Toast.LENGTH_LONG).show()
            }
        })
        return liveData
    }
}