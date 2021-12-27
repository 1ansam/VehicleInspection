package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.request.SaveSignatureW006Request
import com.yxf.vehicleinspection.bean.request.SaveVerifyInfoW013Request
import com.yxf.vehicleinspection.repository.SignatureRepository
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/11/10
 */
class SignatureViewModel(val repository : SignatureRepository) : ViewModel() {
    fun postSignature(saveSignatureW006Request: SaveSignatureW006Request): LiveData<Boolean> {
        return repository.postSignature(saveSignatureW006Request)
    }
    fun postVerifyInfo(saveVerifyInfoW013Request: SaveVerifyInfoW013Request):LiveData<Boolean>{
        return repository.postVerifyInfo(saveVerifyInfoW013Request)
    }
}
class SignatureViewModelFactory(val repository : SignatureRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignatureViewModel::class.java)){
            return SignatureViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }

}