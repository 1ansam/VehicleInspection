package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemR006Response
import com.yxf.vehicleinspection.repository.VehicleInspectionItemRepository

/**
 *   author:yxf
 *   time:2021/11/4
 */
class VehicleInspectionItemViewModel(val repository: VehicleInspectionItemRepository) : ViewModel(){
    fun getVehicleInspectionItem(Lsh : String): LiveData<List<VehicleInspectionItemR006Response>> {
        return repository.getVehicleInspectionItem(Lsh)
    }
}
class VehicleInspectionItemViewModelFactory(val repository: VehicleInspectionItemRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleInspectionItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleInspectionItemViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }

}