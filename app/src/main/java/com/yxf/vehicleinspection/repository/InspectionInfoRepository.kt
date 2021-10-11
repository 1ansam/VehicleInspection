package com.yxf.vehicleinspection.repository

import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.bean.InspectionInfoBean
import com.yxf.vehicleinspection.bean.InspectionInfoItemBean


/**
 *   author:yxf
 *   time:2021/9/29
 */
class InspectionInfoRepository {
    val inspectionInfoData : MutableLiveData<ArrayList<InspectionInfoBean>> = getData()
    private fun getData() : MutableLiveData<ArrayList<InspectionInfoBean>> {
        val liveData = MutableLiveData<ArrayList<InspectionInfoBean>>()
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