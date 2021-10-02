package com.yxf.vehicleinspection.repository

import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.bean.Data
import com.yxf.vehicleinspection.bean.DataJson
import com.yxf.vehicleinspection.service.DataService
import com.yxf.vehicleinspection.singleton.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/9/29
 */
class PersonInspectionRepository {
    val personInspectionData = getData()
    private fun getData2(): MutableLiveData<ArrayList<BaseInfo_Hand>> {
        val liveData = MutableLiveData<ArrayList<BaseInfo_Hand>>()
        val cars = ArrayList<BaseInfo_Hand>()
        for (index in 0 until 10) {
            cars.add(BaseInfo_Hand("$index", "20210928160801", "小型汽车", "晋K7058N", "在用车定检", "年检"))
        }
        liveData.value = cars
        return liveData
    }
    fun getData() : MutableLiveData<ArrayList<Data>>{
        val liveData = MutableLiveData<ArrayList<Data>>()

        val dataService = RetrofitService.getRetrofit().create(DataService::class.java)
        val call = dataService.getData()
        call.enqueue(object  : Callback<DataJson>{
            override fun onResponse(call: Call<DataJson>, response: Response<DataJson>) {
                if (response.body()?.code.equals("1")){
                    val modelList = ArrayList<Data>(response.body()!!.data)
                    liveData.value = modelList
                }
            }

            override fun onFailure(call: Call<DataJson>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return liveData
    }
}