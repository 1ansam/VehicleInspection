package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.ModerationQueueR013Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.repository.VehicleQueueRepository

/**
 *   author:yxf
 *   time:2021/9/29
 */
class VehicleQueueViewModel(private val repository: VehicleQueueRepository) : ViewModel() {

    fun getInspectionQueue(hphm: String): LiveData<List<VehicleQueueR002Response>> {
        return repository.getInspectionQueue(hphm)
    }
    fun getChargeQueue(hphm: String) : LiveData<List<VehicleQueueR002Response>> {
        return repository.getChargeQueue(hphm)
    }

}

class VehicleQueueViewModelFactory(private val repository: VehicleQueueRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleQueueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleQueueViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}