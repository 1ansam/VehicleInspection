package com.yxf.vehicleinspection.viewModel

import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.request.SaveVehicleInfoW003Request
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.repository.DataDictionaryRepository
import com.yxf.vehicleinspection.repository.RegisterRepository

/**
 *   author:yxf
 *   time:2021/12/2
 */
class RegisterViewModel(val registerRepository: RegisterRepository) : ViewModel() {
    fun getVehicleInfo(Hphm : String, Hpzl : String, Clsbdh : String, Ajywlb : String, Hjywlb : String, Jyjgbh : String): LiveData<VehicleAllInfoR022Response> {
        return registerRepository.getVehicleInfo(Hphm, Hpzl, Clsbdh, Ajywlb, Hjywlb, Jyjgbh)
    }
    fun postSaveVehicleInfo(saveVehicleInfoW003Request: SaveVehicleInfoW003Request) : LiveData<Boolean>{
        return registerRepository.postSaveVehicleInfo(saveVehicleInfoW003Request)
    }

    val textMap = registerRepository.getTextMap()
    val textList = registerRepository.getTextList()
    val flList = registerRepository.getFlList()
    val spinnerMap = registerRepository.getSpinnerMap()
}
class RegisterViewModelFactory(val registerRepository: RegisterRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.equals(RegisterViewModel::class.java)){
            return RegisterViewModel(registerRepository) as T
        }else{
            throw IllegalArgumentException("未知ViewModel")
        }
    }

}