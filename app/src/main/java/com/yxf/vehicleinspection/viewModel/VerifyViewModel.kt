package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.ModerationQueueR013Response
import com.yxf.vehicleinspection.bean.response.VehicleImageR007Response
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response
import com.yxf.vehicleinspection.repository.VerifyRepository

/**
 *   author:yxf
 *   time:2021/12/17
 */
class VerifyViewModel(private val verifyRepository: VerifyRepository) : ViewModel() {
    var shyw = 0
    fun getVerifyDataQueue(hphm : String, shyw : String): LiveData<List<ModerationQueueR013Response>> {
        return verifyRepository.getVerifyDataQueue(hphm, shyw)
    }
    fun getVehicleImage(ajlsh : String, hjlsh : String): LiveData<List<VehicleImageR007Response>> {
        return verifyRepository.getVehicleImage(ajlsh,hjlsh)
    }
    fun getVehicleVideo(ajlsh: String, hjlsh: String): LiveData<List<VehicleVideoR008Response>> {
        return verifyRepository.getVehicleVideo(ajlsh,hjlsh)
    }
}
class VerifyViewModelFactory(private val verifyRepository: VerifyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerifyViewModel::class.java)){
            return VerifyViewModel(verifyRepository) as T
        }else{
            throw IllegalArgumentException("未知ViewModel")
        }
    }
}