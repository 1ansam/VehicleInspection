package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.UserInfoResponse
import com.yxf.vehicleinspection.repository.UserInfoRepository
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/10/12
 */
class LoginViewModel(private val repository: UserInfoRepository) : ViewModel() {
    fun isLogin(username: String, password: String): LiveData<Boolean> {
        return repository.getUserLogin(username,password)
    }
    fun getUserInfo() : LiveData<List<UserInfoResponse>>{
        return repository.getUserInfo()
    }
}
class LoginViewModelFactory(val repository: UserInfoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")

    }

}