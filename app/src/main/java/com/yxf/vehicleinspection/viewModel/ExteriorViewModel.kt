package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.ImageItemResponse
import com.yxf.vehicleinspection.repository.ImageItemRepository

/**
 *   author:yxf
 *   time:2021/11/4
 */
class ExteriorViewModel(val imageItemRepository: ImageItemRepository) : ViewModel(){
    fun getImageItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String): LiveData<List<ImageItemResponse>> {
        return imageItemRepository.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }
}
class ExteriorViewModelFactory(val imageItemRepository: ImageItemRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExteriorViewModel::class.java)){
            return ExteriorViewModel(imageItemRepository) as T
        }
        throw IllegalAccessException("未知ViewModel")
    }

}