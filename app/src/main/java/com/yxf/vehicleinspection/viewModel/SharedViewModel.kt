package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse

/**
 *   author:yxf
 *   time:2021/10/26
 */
class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<VehicleQueueResponse>()
    fun select(vehicleQueueResponse: VehicleQueueResponse){
        selected.value = vehicleQueueResponse
    }
}