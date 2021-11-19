package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.VehicleFeature
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.ArtificialProjectR020Response
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.bean.response.ServerTimeR011Response
import com.yxf.vehicleinspection.repository.InspectionItemRepository
import com.yxf.vehicleinspection.repository.ServerTimeRepository
import com.yxf.vehicleinspection.utils.uploadFile
import okhttp3.RequestBody
import java.io.File

/**
 *   author:yxf
 *   time:2021/11/4
 */
class InspectionItemViewModel(private val inspectionItemRepository: InspectionItemRepository,private val serverTimeRepository: ServerTimeRepository) : ViewModel(){
    fun getImageItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String): LiveData<List<ImageItemR017Response>> {
        return inspectionItemRepository.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }

    fun getSelectItemData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String): LiveData<ArtificialProjectR020Response> {
        return inspectionItemRepository.getSelectItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }
    fun getServerTime() : LiveData<ServerTimeR011Response>{
        return serverTimeRepository.getServerTime()
    }
    fun postInspectionPhotoW007(list : List<InspectionPhotoW007Request>) : LiveData<Boolean>{
        return inspectionItemRepository.postInspectionPhotoW007(list)
    }
    fun <T> postArtificialProjectW011(list : List<ArtificialProjectW011Request<T>>): MutableLiveData<Boolean> {
        return inspectionItemRepository.postArtificialProjectW011(list)
    }
    fun postSaveVideoW008(saveVideoW008Request: SaveVideoW008Request): LiveData<Boolean>{
        return inspectionItemRepository.postSaveVideoW008(saveVideoW008Request)
    }
    fun postProjectStartW010(projectStartW010Request: ProjectStartW010Request):LiveData<Boolean>{
        return inspectionItemRepository.postProjectStartW010(projectStartW010Request)
    }
    fun postProjectEndW012(projectEndW012Request: ProjectEndW012Request): LiveData<Boolean>{
        return inspectionItemRepository.postProjectEndW012(projectEndW012Request)
    }
    fun postUploadFile(file : File,requestBody: RequestBody): LiveData<String>{
        return inspectionItemRepository.postUploadFile(file,requestBody)
    }
    fun getPostProjectEndData(Lsh: String, Jyjgbh : String, Jcxh : String, Jccs : Int, Hphm : String, Hpzl : String, Clsbdh : String, Jyxm : String, Gwjysbbh : String, Jssj : String, Ajywlb : String, Hjywlb : String, AjJkxlh :String):ProjectEndW012Request{
        return ProjectEndW012Request(Lsh,Jyjgbh,Jcxh, Jccs, Hphm, Hpzl, Clsbdh, Jyxm, Gwjysbbh, Jssj, Ajywlb, Hjywlb, AjJkxlh)
    }
    fun getPostVideoData(Lsh : String, Jcxh : String, Jccs : Int, Hphm : String, Hpzl : String, Jcxm : String, Spbhaj : String, Spbhhj : String, Ajywlb : String, Hjywlb : String, Jcrq : String, Jcsj : String, Jckssj : String, Jcjssj : String, Lxxx : String, Clpp : String, Czdw : String, Bcaj : String, BcHj : String, Hjdlsj : String, Lxdz : String, Lxbz : String,) : SaveVideoW008Request{
        return SaveVideoW008Request(0,Lsh,Jcxh, Jccs, Hphm, Hpzl, Jcxm, Spbhaj, Spbhhj, Ajywlb, Hjywlb, Jcrq, Jcsj, Jckssj, Jcjssj, Lxxx, Clpp, Czdw, Bcaj, BcHj, Hjdlsj, Lxdz, Lxbz)
    }




}
class InspectionItemViewModelFactory(private val inspectionItemRepository: InspectionItemRepository,
                                     private val serverTimeRepository: ServerTimeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InspectionItemViewModel::class.java)){
            return InspectionItemViewModel(inspectionItemRepository,serverTimeRepository) as T
        }
        throw IllegalAccessException("未知ViewModel")
    }

}