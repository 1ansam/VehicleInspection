package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.*
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response
import com.yxf.vehicleinspection.repository.SystemParamsRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/11/11
 */
class SystemParamsViewModel(private val systemParamsRepository: SystemParamsRepository) : ViewModel() {
    val insertEnd = MutableLiveData<Boolean>()
    fun getSystemParamsData() : Pair<LiveData<List<SystemParamsR015Response>>, LiveData<String>> {
        return systemParamsRepository.getSystemParamsData()
    }
    fun getSystemParamsDataFromDb() : LiveData<List<SystemParamsR015Response>> {
        return systemParamsRepository.getSystemParamsFromDb()
    }
    fun getSystemParamsDataFromDb(Sjlb: String) : LiveData<SystemParamsR015Response> {
        return systemParamsRepository.getSystemParamsFromDb(Sjlb)
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
    fun getLshSzm() : LiveData<String>{
        return systemParamsRepository.getLshSzm()
    }
    fun insertSystemParams(systemParamsList: List<SystemParamsR015Response>) = viewModelScope.launch {
        val systemParams = systemParamsRepository.insertSystemParams(systemParamsList)
        if (systemParamsRepository.rowNum == systemParams.size){
            insertEnd.value = true
        }
    }

    fun updateSystemParams(systemParamsList: List<SystemParamsR015Response>) = viewModelScope.launch {
        systemParamsRepository.updateSystemParams(systemParamsList)
    }
    fun deleteSystemParams() = viewModelScope.launch {
        systemParamsRepository.deleteSystemParams()
    }

}
class SystemParamsViewModelFactory(private val systemParamsRepository: SystemParamsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        if (modelClass.isAssignableFrom(SystemParamsViewModel::class.java)){
            return SystemParamsViewModel(systemParamsRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }

}