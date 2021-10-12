package com.yxf.vehicleinspection.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.bean.InspectionInfoBean
import com.yxf.vehicleinspection.bean.InspectionInfoItemBean


/**
 *   author:yxf
 *   time:2021/9/29
 */
class InspectionInfoRepository {
    val inspectionInfoData : LiveData<List<InspectionInfoBean>> = getData()
    private fun getData() : LiveData<List<InspectionInfoBean>> {
        val liveData = MutableLiveData<List<InspectionInfoBean>>()
        val itemBeanList = ArrayList<InspectionInfoItemBean>()
        for (index in 0 until 20){
            val itemBean = InspectionInfoItemBean("name$index","value$index")
            itemBeanList.add(itemBean)
        }
        val modelList = ArrayList<InspectionInfoBean>()
        for (index in 0 until 10){
            val model = InspectionInfoBean("$index",itemBeanList)
            modelList.add(model)
        }
        liveData.value = modelList
        return liveData
    }
}