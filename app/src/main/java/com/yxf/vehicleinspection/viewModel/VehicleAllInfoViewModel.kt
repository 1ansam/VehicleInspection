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
     *   @param Lsh 流水号 VARCHAR(20) 按流水号查询时其他参数可空（空字符串）
     *   @param Hphm 号牌号码 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Hpzl 号牌种类 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Clsbdh 车辆识别代号 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Xszbh 行驶证编号 按行驶证编号查询时其他参数可空（空字符串）
     *   @return LiveData<List<VehicleAllInfoResponse>>
     */
    fun getVehicleAllInfo(Lsh : String, Hphm : String, Hpzl : String, Clsbdh : String, Xszbh : String): LiveData<List<VehicleAllInfoR005Response>> {
        return repository.getVehicleAllInfoRepository(Lsh, Hphm, Hpzl, Clsbdh, Xszbh)
    }
}
class VehicleAllInfoViewModelFactory(private val repositoryAll: VehicleAllInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleAllInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleAllInfoViewModel(repositoryAll) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}