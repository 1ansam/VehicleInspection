package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response
import com.yxf.vehicleinspection.repository.DataDictionaryRepository
import com.yxf.vehicleinspection.repository.SystemParamsRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/10/8
 */
class DataDictionaryViewModel(private val dataDictionaryRepository: DataDictionaryRepository) : ViewModel() {
    fun getDataDictionary(): LiveData<List<DataDictionaryR003Response>>{
        return dataDictionaryRepository.getDictionaryData()
    }
    fun insertDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>) = viewModelScope.launch {
        dataDictionaryRepository.insertDataDictionary(dataDictionaryListResponse)
    }
    fun updateDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>) = viewModelScope.launch {
        dataDictionaryRepository.updateDataDictionary(dataDictionaryListResponse)
    }
    fun getDataDictionaryExist(): LiveData<DataDictionaryR003Response> {
        return dataDictionaryRepository.getDataDictionaryExist()
    }
    fun getMc(Fl : String, Dm : String) : LiveData<String>{
        return dataDictionaryRepository.getMc(Fl, Dm)
    }
    fun getMcList(Fl: String) : LiveData<List<String>>{
        return dataDictionaryRepository.getMcList(Fl)
    }
    fun getListFromFl(Fl: String): LiveData<List<DataDictionaryR003Response>> {
        return dataDictionaryRepository.getListFromFl(Fl)
    }

    fun getDm(Fl : String, FlMc : String) : LiveData<String>{
        return dataDictionaryRepository.getDM(Fl, FlMc)
    }







}
class DataDictionaryViewModelFactory(private val dataDictionaryRepository: DataDictionaryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataDictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataDictionaryViewModel(dataDictionaryRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}