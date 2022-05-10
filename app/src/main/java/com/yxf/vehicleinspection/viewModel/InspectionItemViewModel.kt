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
    /**
     * 从服务器获取所有用户信息
     */
    fun getUserInfo() : LiveData<List<UserInfoR001Response>> {
        return inspectionItemRepository.getUserInfo()
    }
    /**
     * 从服务器获取检验照片信息
     * @param Jyxm 检验项目
     * @param Ajywlb 安检业务类别
     * @param Hjywlb 环检业务类别
     * @param Ajlsh 安检流水号
     * @param Hjlsh 环检流水号
     */
    fun getImageItemData(Jyxm : String, Ajywlb : String, Hjywlb : String,Ajlsh : String,
                         Hjlsh : String,): LiveData<List<ImageItemR017Response>> {
        return inspectionItemRepository.getImageItemData(Jyxm, Ajywlb, Hjywlb,Ajlsh, Hjlsh)
    }
    /**
     * 从服务器获取人工检验项目
     * @param Jyxm 检验项目
     * @param Ajywlb 安检业务类别
     * @param Hjywlb 环检业务类别
     * @param Ajlsh 安检流水号
     * @param Hjlsh 环检流水号
     */
    fun getSelectItemData(Jyxm : String, Ajywlb : String, Hjywlb : String,Ajlsh : String,
                          Hjlsh : String,): LiveData<List<ArtificialProjectR020Response>> {
        return inspectionItemRepository.getSelectItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh)
    }
    fun getServerTime() : LiveData<ServerTimeR011Response>{
        return serverTimeRepository.getServerTime()
    }
    /**
     * 联网查询内需要填写项目列表
     */
    fun getNetworkQueryInfoName(): List<String>{
        return inspectionItemRepository.getNetworkQueryInfoName()
    }
    /**
     * 上传机动车检验照片信息
     */
    fun postInspectionPhotoW007(list : List<InspectionPhotoW007Request>) : LiveData<Boolean>{
        return inspectionItemRepository.postInspectionPhotoW007(list)
    }
    /**
     * 上传机动车检验照片信息
     */
    fun postInspectionPhotoW007(inspectionPhotoW007Request: InspectionPhotoW007Request): LiveData<Boolean> {
        return inspectionItemRepository.postInspectionPhotoW007(inspectionPhotoW007Request)
    }
    /**
     * 上传人工检验项目信息
     *   @param T 检测数据bean 见See Also
     *   @see ExteriorArtificialProjectRequest
     *   @see ChassisArtificialProjectRequest
     *   @see DynamicArtificialProjectRequest
     *   @see NetworkQueryArtificialProjectRequest
     *   @see UniqueArtificialProjectRequest
     */
    fun <T> postArtificialProjectW011(list : List<ArtificialProjectW011Request<T>>): MutableLiveData<Boolean> {
        return inspectionItemRepository.postArtificialProjectW011(list)
    }
    /**
     * 上传保存视频信息
     */
    fun postSaveVideoW008(saveVideoW008Request: SaveVideoW008Request): LiveData<Boolean>{
        return inspectionItemRepository.postSaveVideoW008(saveVideoW008Request)
    }
    /**
     * 上传项目开始信息
     */
    fun postProjectStartW010(projectStartW010Request: ProjectStartW010Request):LiveData<Boolean>{
        return inspectionItemRepository.postProjectStartW010(projectStartW010Request)
    }
    /**
     * 上传项目结束信息
     */
    fun postProjectEndW012(projectEndW012Request: ProjectEndW012Request): LiveData<Boolean>{
        return inspectionItemRepository.postProjectEndW012(projectEndW012Request)
    }
    /**
     * 上传文件
     */
    fun postUploadFile(file : File,requestBody: RequestBody): LiveData<String>{
        return inspectionItemRepository.postUploadFile(file,requestBody)
    }
    /**
     * 触发摄像头拍照
     */
    fun postTakePhoto(takePhotoW009Request: TakePhotoW009Request): LiveData<Boolean>{
        return inspectionItemRepository.postTakePhoto(takePhotoW009Request)
    }
    fun getPostProjectEndData(Jyjgbh : String, Jcxh : String, Hphm : String, Hpzl : String, Clsbdh : String, Jyxm : String, Gwjysbbh : String, Jssj : String, Ajywlb : String, Hjywlb : String, AjJkxlh :String, Ajlsh: String, Hjlsh: String, Ajjccs: Int, Hjjccs: Int):ProjectEndW012Request{
        return ProjectEndW012Request(Jyjgbh,Jcxh,  Hphm, Hpzl, Clsbdh, Jyxm, Gwjysbbh, Jssj, Ajywlb, Hjywlb, AjJkxlh,Ajlsh, Hjlsh, Ajjccs, Hjjccs)
    }
    fun getPostVideoData( Jcxh : String, Hphm : String, Hpzl : String, Jcxm : String, Spbhaj : String, Spbhhj : String, Ajywlb : String, Hjywlb : String, Jcrq : String, Jcsj : String, Jckssj : String, Jcjssj : String, Lxxx : String, Clpp : String, Czdw : String, Hjdlsj : String, Lxdz : String, Lxbz : String,Ajlsh : String,Hjlsh : String, Ajjccs: Int, Hjjccs: Int ) : SaveVideoW008Request{
        return SaveVideoW008Request(0,Jcxh,  Hphm, Hpzl, Jcxm, Spbhaj, Spbhhj, Ajywlb, Hjywlb, Jcrq, Jcsj, Jckssj, Jcjssj, Lxxx, Clpp, Czdw, Hjdlsj, Lxdz, Lxbz,Ajlsh, Hjlsh, Ajjccs, Hjjccs)
    }
    /**
     * 查询人工检验项目最短时间
     */
    fun getLeastestTime(ajcx : String, jyxm : String): MutableLiveData<LeastestTimeR019Response> {
        return inspectionItemRepository.getLeastestTime(ajcx, jyxm)
    }
    /**
     * 开始上线
     */
    fun startOnline(startOnlineW015Request: StartOnlineW015Request) : LiveData<Boolean>{
        return inspectionItemRepository.startOnline(startOnlineW015Request)
    }
    /**
     * 获取上线状态信息
     */
    fun getOnlineStatus(onlineStatusR024Request: OnlineStatusR024Request) : LiveData<OnlineStatusR024Response> {
        return inspectionItemRepository.getOnlineStatus(onlineStatusR024Request)
    }




}
class InspectionItemViewModelFactory(private val inspectionItemRepository: InspectionItemRepository,
                                     private val serverTimeRepository: ServerTimeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InspectionItemViewModel::class.java)){
            return InspectionItemViewModel(inspectionItemRepository,serverTimeRepository) as T
        }
        throw IllegalAccessException("未知ViewModel")
    }

}