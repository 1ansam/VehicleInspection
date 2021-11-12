package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response
import com.yxf.vehicleinspection.repository.SystemParamsRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/11/11
 */
class SystemParamsViewModel(private val systemParamsRepository: SystemParamsRepository) : ViewModel() {
    fun getSystemParamsData() : LiveData<List<SystemParamsR015Response>> {
        return systemParamsRepository.getSystemParamsData()
    }

    fun getSystemParamsDataExist() : LiveData<SystemParamsR015Response> {
        return systemParamsRepository.getSystemParamsDataExist()
    }

    fun getJyjgbh(Sjlb : String) : LiveData<String>{
        return systemParamsRepository.getJyjgbh(Sjlb)
    }

    fun getWebPass(Sjlb : String) : LiveData<String>{
        return systemParamsRepository.getWebPass(Sjlb)
    }
    fun insertSystemParams(systemParamsList: List<SystemParamsR015Response>) = viewModelScope.launch {
        systemParamsRepository.insertSystemParams(systemParamsList)
    }

    fun updateSystemParams(systemParamsList: List<SystemParamsR015Response>) = viewModelScope.launch {
        systemParamsRepository.updateSystemParams(systemParamsList)
    }
    fun deleteSystemParams() = viewModelScope.launch {
        systemParamsRepository.deleteSystemParams()
    }

}
class SystemParamsViewModelFactory(private val systemParamsRepository: SystemParamsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T{
        if (modelClass.isAssignableFrom(SystemParamsViewModel::class.java)){
            return SystemParamsViewModel(systemParamsRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }

}