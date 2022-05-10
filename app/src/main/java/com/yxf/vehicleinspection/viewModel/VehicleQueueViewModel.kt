package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.*
import com.yxf.vehicleinspection.bean.response.ModerationQueueR013Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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

    suspend fun getSuspendInspectionQueue(hphm: String) : Flow<List<VehicleQueueR002Response>> {
        return repository.getSuspendInspectionQueue(hphm)
    }


}

class VehicleQueueViewModelFactory(private val repository: VehicleQueueRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleQueueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleQueueViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}