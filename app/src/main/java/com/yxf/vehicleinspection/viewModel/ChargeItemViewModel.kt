package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.*
import com.yxf.vehicleinspection.bean.response.ChargeItemR004Response
import com.yxf.vehicleinspection.repository.ChargeItemRepository
import kotlinx.coroutines.launch

/**
 *   author:yxf
 *   time:2021/12/21
 */
class ChargeItemViewModel(private val chargeItemRepository: ChargeItemRepository) : ViewModel() {
    var insertEnd = MutableLiveData<Boolean>()

    fun getChargeItem(): LiveData<List<ChargeItemR004Response>> {
        return chargeItemRepository.getChargeItem()
    }

    fun getChargeItemFromDb(): LiveData<List<ChargeItemR004Response>> {
        return chargeItemRepository.getChargeItemFromDb()
    }

    fun insertChargeItem(chargeItemItemR004Response: List<ChargeItemR004Response>) = viewModelScope.launch {
        chargeItemRepository.insertChargeItem(chargeItemItemR004Response)
        if (chargeItemRepository.insertChargeItem(chargeItemItemR004Response).size == chargeItemRepository.rowNum){
            insertEnd.value = true
        }
    }

    fun updateChargeItem(chargeItemItemR004Response: List<ChargeItemR004Response>) = viewModelScope.launch {
        chargeItemRepository.updateChargeItem(chargeItemItemR004Response)
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