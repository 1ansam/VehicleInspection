package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yxf.vehicleinspection.repository.JsCsCodeRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/10/8
 */
class JsCsCodeViewModel(private val repository: JsCsCodeRepository) : ViewModel() {
    fun downloadJsCsCode() = viewModelScope.launch {
        repository.downloadJsCsCode()
    }
}
class JsCsCodeViewModelFactory(private val repository: JsCsCodeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JsCsCodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JsCsCodeViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}