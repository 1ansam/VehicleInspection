package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.ArtificialProjectR020Response
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.bean.response.ServerTimeR011Response
import com.yxf.vehicleinspection.repository.InspectionItemRepository
import com.yxf.vehicleinspection.repository.ServerTimeRepository

/**
 *   author:yxf
 *   time:2021/11/4
 */
class InspectionItemViewModel(private val inspectionItemRepository: InspectionItemRepository,private val serverTimeRepository: ServerTimeRepository) : ViewModel(){
    fun getImageItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String): LiveData<List<ImageItemR017Response>> {
        return inspectionItemRepository.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }
    fun postInspectionPhotoW007(list : List<InspectionPhotoW007Request>) : LiveData<Boolean>{
        return inspectionItemRepository.postInspectionPhotoW007(list)
    }
    fun getSelectItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String): LiveData<ArtificialProjectR020Response> {
        return inspectionItemRepository.getSelectItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }
    fun getServerTime() : LiveData<ServerTimeR011Response>{
        return serverTimeRepository.getServerTime()
    }
    fun <T> postArtificialProjectW011(list : List<ArtificialProjectW011Request<T>>): MutableLiveData<Boolean> {
        return inspectionItemRepository.postArtificialProjectW011(list)
    }
    fun postSaveVideoW008(saveVideoW008Request: SaveVideoW008Request): LiveData<Boolean>{
        return inspectionItemRepository.postSaveVideoW008(saveVideoW008Request)
    }
    fun postProjectEndW012(projectEndW012Request: ProjectEndW012Request): LiveData<Boolean>{
        return inspectionItemRepository.postProjectEndW012(projectEndW012Request)
    }




}
class InspectionItemViewModelFactory(private val inspectionItemRepository: InspectionItemRepository,val serverTimeRepository: ServerTimeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InspectionItemViewModel::class.java)){
            return InspectionItemViewModel(inspectionItemRepository,serverTimeRepository) as T
        }
        throw IllegalAccessException("未知ViewModel")
    }

}