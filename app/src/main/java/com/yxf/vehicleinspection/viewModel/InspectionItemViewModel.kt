package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.*
import com.yxf.vehicleinspection.repository.InspectionItemRepository
import com.yxf.vehicleinspection.repository.ServerTimeRepository
import okhttp3.RequestBody
import java.io.File

/**
 *   author:yxf
 *   time:2021/11/4
 */
class InspectionItemViewModel(private val inspectionItemRepository: InspectionItemRepository,private val serverTimeRepository: ServerTimeRepository) : ViewModel(){
    fun getUserInfo() : LiveData<List<UserInfoR001Response>> {
        return inspectionItemRepository.getUserInfo()
    }
    fun getImageItemData(Jyxm : String, Ajywlb : String, Hjywlb : String,Ajlsh : String,
                         Hjlsh : String,): LiveData<List<ImageItemR017Response>> {
        return inspectionItemRepository.getImageItemData(Jyxm, Ajywlb, Hjywlb,Ajlsh, Hjlsh)
    }

    fun getSelectItemData(Jyxm : String, Ajywlb : String, Hjywlb : String,Ajlsh : String,
                          Hjlsh : String,): LiveData<List<ArtificialProjectR020Response>> {
        return inspectionItemRepository.getSelectItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh)
    }
    fun getServerTime() : LiveData<ServerTimeR011Response>{
        return serverTimeRepository.getServerTime()
    }
    fun getNetworkQueryInfoName(): List<String>{
        return inspectionItemRepository.getNetworkQueryInfoName()
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
    fun postTakePhoto(takePhotoW009Request: TakePhotoW009Request): LiveData<Boolean>{
        return inspectionItemRepository.postTakePhoto(takePhotoW009Request)
    }
    fun getPostProjectEndData(Jyjgbh : String, Jcxh : String, Hphm : String, Hpzl : String, Clsbdh : String, Jyxm : String, Gwjysbbh : String, Jssj : String, Ajywlb : String, Hjywlb : String, AjJkxlh :String, Ajlsh: String, Hjlsh: String, Ajjccs: Int, Hjjccs: Int):ProjectEndW012Request{
        return ProjectEndW012Request(Jyjgbh,Jcxh,  Hphm, Hpzl, Clsbdh, Jyxm, Gwjysbbh, Jssj, Ajywlb, Hjywlb, AjJkxlh,Ajlsh, Hjlsh, Ajjccs, Hjjccs)
    }
    fun getPostVideoData( Jcxh : String, Hphm : String, Hpzl : String, Jcxm : String, Spbhaj : String, Spbhhj : String, Ajywlb : String, Hjywlb : String, Jcrq : String, Jcsj : String, Jckssj : String, Jcjssj : String, Lxxx : String, Clpp : String, Czdw : String, Hjdlsj : String, Lxdz : String, Lxbz : String,Ajlsh : String,Hjlsh : String, Ajjccs: Int, Hjjccs: Int ) : SaveVideoW008Request{
        return SaveVideoW008Request(0,Jcxh,  Hphm, Hpzl, Jcxm, Spbhaj, Spbhhj, Ajywlb, Hjywlb, Jcrq, Jcsj, Jckssj, Jcjssj, Lxxx, Clpp, Czdw, Hjdlsj, Lxdz, Lxbz,Ajlsh, Hjlsh, Ajjccs, Hjjccs)
    }
    fun getLeastestTime(ajcx : String, jyxm : String): MutableLiveData<LeastestTimeR019Response> {
        return inspectionItemRepository.getLeastestTime(ajcx, jyxm)
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