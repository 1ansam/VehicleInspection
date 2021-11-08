package com.yxf.vehicleinspection.utils

import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.singleton.GsonSingleton

/**
 *   author:yxf
 *   time:2021/10/12
 */
object JsonDataHelper {
    fun <T> getJsonData(element : T) : String{
        val requestArray = ArrayList<T>()
        requestArray.add(element)
        return GsonSingleton.getGson().toJson(CommonRequest(requestArray))
    }
    fun <T> getJsonData(elements : List<T>) : String{
        val requestArray = ArrayList<T>()
        for (element in elements){
            requestArray.add(element)
        }
        return GsonSingleton.getGson().toJson(CommonRequest(requestArray))
    }
}