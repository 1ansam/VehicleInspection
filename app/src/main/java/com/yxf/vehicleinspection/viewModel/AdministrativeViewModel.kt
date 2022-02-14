package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.*
import com.yxf.vehicleinspection.bean.response.AdministrativeR023Response
import com.yxf.vehicleinspection.repository.AdministrativeRepository
import com.yxf.vehicleinspection.repository.DataDictionaryRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/12/14
 */
class AdministrativeViewModel(val administrativeRepository: AdministrativeRepository) :
    ViewModel() {
    val insertEnd = MutableLiveData<Boolean>()
    /**
     * 从服务器获取行政区划数据
     */
    fun getAdministrativeList() : LiveData<List<AdministrativeR023Response>>{
        return administrativeRepository.getAdministrativeList()
    }
    fun insertAdministrativeList(administrativeList: List<AdministrativeR023Response>) = viewModelScope.launch {
        administrativeRepository.insertAdministrativeList(administrativeList)
        if (administrativeRepository.rowNum == administrativeRepository.insertAdministrativeList(administrativeList).size){
            insertEnd.value = true
        }
    }
    fun deleteAdministrative() = viewModelScope.launch {
        administrativeRepository.deleteAdministrative()
    }
    /**
     * 从数据库按名称获取行政区划
     * @param xzqhmc 行政区划名称
     */
    fun getAdministrativeListFromMc(xzqhmc : String) : LiveData<List<AdministrativeR023Response>>{
        return administrativeRepository.getAdministrativeListFromMc(xzqhmc)
    }
}
class AdministrativeViewModelFactory(private val administrativeRepository: AdministrativeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdministrativeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdministrativeViewModel(administrativeRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}