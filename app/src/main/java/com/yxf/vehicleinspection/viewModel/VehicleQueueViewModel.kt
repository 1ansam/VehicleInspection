package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.repository.VehicleQueueRepository

/**
 *   author:yxf
 *   time:2021/9/29
 */
class VehicleQueueViewModel(private val repository: VehicleQueueRepository) : ViewModel() {
    private lateinit var  queueData : LiveData<List<VehicleQueueResponse>>
    private lateinit var verifyQueueData: LiveData<List<VehicleQueueResponse>>
    fun getDataQueue(hphm : String) : LiveData<List<VehicleQueueResponse>> {
        queueData = repository.getInspectionDataQueue(hphm)
        return queueData
    }
    fun getVerifyDataQueue(hphm: String) : LiveData<List<VehicleQueueResponse>>{
        verifyQueueData = repository.getVerifyDataQueue(hphm)
        return verifyQueueData
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