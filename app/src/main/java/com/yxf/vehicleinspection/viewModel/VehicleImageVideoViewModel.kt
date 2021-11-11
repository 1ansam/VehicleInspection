package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.VehicleImageR007Response
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response
import com.yxf.vehicleinspection.repository.VehicleImageRepository
import com.yxf.vehicleinspection.repository.VehicleVideoRepository

/**
 *   author:yxf
 *   time:2021/10/13
 */
class VehicleImageVideoViewModel(
    val imageRepository : VehicleImageRepository,
    val videoRepository : VehicleVideoRepository
) : ViewModel() {
    fun getImageData(Lsh : String): LiveData<List<VehicleImageR007Response>> {
        return imageRepository.getImageData(Lsh)
    }

    fun getVideoData(Lsh: String, Jccs : String) : LiveData<List<VehicleVideoR008Response>>{
        return videoRepository.getVehicleVideo(Lsh, Jccs)
    }

}
class VehicleImageVideoViewModelFactory(val imageRepository : VehicleImageRepository,
                                        val videoRepository : VehicleVideoRepository
                                        ) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VehicleImageVideoViewModel(imageRepository,videoRepository) as T
    }

}