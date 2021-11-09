package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yxf.vehicleinspection.bean.response.DataDictionaryResponse003
import com.yxf.vehicleinspection.repository.DataDictionaryRepository
import com.yxf.vehicleinspection.repository.JsCsCodeRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/10/8
 */
class WelcomeViewModel(private val repository: DataDictionaryRepository) : ViewModel() {
    fun getDataDictionary(): LiveData<List<DataDictionaryResponse003>>{
        return repository.getDictionaryData()
    }
    fun insertData(dataDictionaryList: List<DataDictionaryResponse003>) = viewModelScope.launch {
        repository.insertData(dataDictionaryList)
    }
    fun updateData(dataDictionaryList: List<DataDictionaryResponse003>) = viewModelScope.launch {
        repository.updateData(dataDictionaryList)
    }
    fun getDataExist(): LiveData<DataDictionaryResponse003> {
        return repository.getDataExist()
    }
}
class WelcomeViewModelFactory(private val repository: DataDictionaryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}