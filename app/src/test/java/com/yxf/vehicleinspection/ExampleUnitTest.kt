package com.yxf.vehicleinspection

import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.request.VehicleAdmissionRequest
import com.yxf.vehicleinspection.singleton.GsonSingleton
import org.json.JSONArray
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest(){
    @Test
    fun main(){
        val commonRequest = CommonRequest<VehicleAdmissionRequest>(ArrayList())
        val string = GsonSingleton.getGson().toJson(commonRequest)
        println(string)
    }
}

