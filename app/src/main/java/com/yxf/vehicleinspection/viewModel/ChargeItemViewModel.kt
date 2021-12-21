package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.*
import com.yxf.vehicleinspection.bean.response.ChargeR004Response
import com.yxf.vehicleinspection.repository.ChargeItemRepository
import kotlinx.coroutines.launch

/**
 *   author:yxf
 *   time:2021/12/21
 */
class ChargeItemViewModel(private val chargeItemRepository: ChargeItemRepository) : ViewModel() {
    var insertEnd = MutableLiveData<Boolean>()

    fun getChargeItem(): LiveData<List<ChargeR004Response>> {
        return chargeItemRepository.getChargeItem()
    }

    fun getChargeItemFromDb(): LiveData<List<ChargeR004Response>> {
        return chargeItemRepository.getChargeItemFromDb()
    }

    fun insertChargeItem(chargeItemR004Response: List<ChargeR004Response>) = viewModelScope.launch {
        chargeItemRepository.insertChargeItem(chargeItemR004Response)
        if (chargeItemRepository.insertChargeItem(chargeItemR004Response).size == chargeItemRepository.rowNum){
            insertEnd.value = true
        }
    }

    fun updateChargeItem(chargeItemR004Response: List<ChargeR004Response>) = viewModelScope.launch {
        chargeItemRepository.updateChargeItem(chargeItemR004Response)
    }

    fun deleteChargeItem() = viewModelScope.launch {
        chargeItemRepository.deleteChargeItem()
    }
}
class ChargeItemViewModelFactory(val chargeItemRepository: ChargeItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChargeItemViewModel::class.java)){
            return ChargeItemViewModel(chargeItemRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}