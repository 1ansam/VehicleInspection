package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.repository.PersonInspectionRepository
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/9/29
 */
class PersonInspectionViewModel(private val repository: PersonInspectionRepository) : ViewModel() {
    fun getDataQueue(hphm : String) : LiveData<List<VehicleQueueResponse>> = repository.getDataQueue(hphm)
}

class PersonInspectionViewModelFactory(private val repository: PersonInspectionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonInspectionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonInspectionViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}