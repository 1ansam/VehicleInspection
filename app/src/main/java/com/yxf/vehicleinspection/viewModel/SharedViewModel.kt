package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse

/**
 *   author:yxf
 *   time:2021/10/26
 */
class SharedViewModel : ViewModel() {
    val selectedBean = MutableLiveData<VehicleQueueResponse>()
    val hostName = MutableLiveData<String>()
    fun selectBean(vehicleQueueResponse: VehicleQueueResponse){
        selectedBean.value = vehicleQueueResponse
    }
    fun setHostName(hostName : String){
        this.hostName.value = hostName
    }
}