package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.InspectionInfoBean
import com.yxf.vehicleinspection.repository.InspectionInfoRepository

/**
 *   author:yxf
 *   time:2021/9/29
 */
class InspectionInfoViewModel(private val repository: InspectionInfoRepository) : ViewModel() {
    var inspectionInfoData: LiveData<List<InspectionInfoBean>> =
        repository.inspectionInfoData
}

class InspectionInfoViewModelFactory(private val repository: InspectionInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InspectionInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InspectionInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}