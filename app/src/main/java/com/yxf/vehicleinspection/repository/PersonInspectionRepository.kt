package com.yxf.vehicleinspection.repository

import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.bean.BaseInfo_Hand

/**
 *   author:yxf
 *   time:2021/9/29
 */
class PersonInspectionRepository {
    val personInspectionData = getData()
    fun getData(): MutableLiveData<ArrayList<BaseInfo_Hand>> {
        val liveData = MutableLiveData<ArrayList<BaseInfo_Hand>>()
        val cars = ArrayList<BaseInfo_Hand>()
        for (index in 0 until 100) {
            cars.add(BaseInfo_Hand("$index", "20210928160801", "小型汽车", "晋K7058N", "在用车定检", "年检"))
        }
        liveData.value = cars
        return liveData
    }
}