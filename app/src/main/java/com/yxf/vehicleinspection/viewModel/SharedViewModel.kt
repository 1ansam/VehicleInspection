package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *   author:yxf
 *   time:2021/10/26
 */
class SharedViewModel : ViewModel() {
    val hostName = MutableLiveData<String>()
    fun setHostName(hostName : String){
        this.hostName.value = hostName
    }
}