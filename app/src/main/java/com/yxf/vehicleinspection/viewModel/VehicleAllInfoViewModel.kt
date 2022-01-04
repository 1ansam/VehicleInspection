package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.repository.VehicleAllInfoRepository

/**
 *   author:yxf
 *   time:2021/11/4
 */
class VehicleAllInfoViewModel(val repository: VehicleAllInfoRepository) : ViewModel(){
    /**
     *   获取车辆信息
     *   @param Hphm 号牌号码
     *   @param Hpzl 号牌种类
     *   @param Clsbdh 车辆识别代号
     *   @param Xszbh 行驶证编号
     *   @param Ajlsh 安检流水号
     *   @param Hjlsh 环检流水号
     *   @return LiveData<List<VehicleAllInfoResponse>>
     */
    fun getVehicleAllInfo(Hphm : String, Hpzl : String, Clsbdh : String, Xszbh : String, Ajlsh : String, Hjlsh : String): LiveData<List<VehicleAllInfoR005Response>> {
        return repository.getVehicleAllInfoRepository(Hphm, Hpzl, Clsbdh, Xszbh, Ajlsh, Hjlsh)
    }
    /**
     * 获取车辆信息
     *   @param Hphm 号牌号码
     *   @param Hpzl 号牌种类
     *   @param Clsbdh 车辆识别代号
     *   @return LiveData<List<VehicleAllInfoResponse>>
     */
    fun getVehicleAllInfo(Hphm : String, Hpzl : String, Clsbdh : String): LiveData<List<VehicleAllInfoR005Response>> {
        return repository.getVehicleAllInfoRepository(Hphm, Hpzl, Clsbdh, "", "", "")
    }
    /**
     * 获取车辆信息
     *   @param Ajlsh 安检流水号
     *   @param Hjlsh 环检流水号
     *   @return LiveData<List<VehicleAllInfoResponse>>
     */
    fun getVehicleAllInfo(Ajlsh : String, Hjlsh : String): LiveData<List<VehicleAllInfoR005Response>> {
        return repository.getVehicleAllInfoRepository("", "", "", "", Ajlsh, Hjlsh)
    }

}
class VehicleAllInfoViewModelFactory(private val repository: VehicleAllInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleAllInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleAllInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("IllegalArgumentException")
    }
}