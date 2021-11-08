package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.request.InspectionPhotoW007Request
import com.yxf.vehicleinspection.bean.response.ImageItemResponse
import com.yxf.vehicleinspection.bean.response.InspectionPhotoW007Response
import com.yxf.vehicleinspection.repository.ExteriorRepository

/**
 *   author:yxf
 *   time:2021/11/4
 */
class ExteriorViewModel(val exteriorRepository: ExteriorRepository) : ViewModel(){
    fun getImageItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String): LiveData<List<ImageItemResponse>> {
        return exteriorRepository.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }
    fun postInspectionPhotoW007(list : List<InspectionPhotoW007Request>) : LiveData<Boolean>{
        return exteriorRepository.postInspectionPhotoW007(list)
    }
}
class ExteriorViewModelFactory(val exteriorRepository: ExteriorRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExteriorViewModel::class.java)){
            return ExteriorViewModel(exteriorRepository) as T
        }
        throw IllegalAccessException("未知ViewModel")
    }

}