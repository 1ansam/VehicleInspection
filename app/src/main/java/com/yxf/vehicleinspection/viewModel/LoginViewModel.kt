package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.bean.response.VersionCodeResponseR021
import com.yxf.vehicleinspection.repository.UserInfoRepository
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/10/12
 */
class LoginViewModel(private val repository: UserInfoRepository) : ViewModel() {
    val isLogin = repository.isLogin
    fun isLoading(username: String, password: String): LiveData<Boolean> {
        return repository.getUserLogin(username,password)
    }
    fun getUser(username: String, password: String):LiveData<UserInfoR001Response>{
        return repository.getUser(username, password)
    }
    fun getVersion(): LiveData<VersionCodeResponseR021> {
        return repository.getVersion()
    }
}
class LoginViewModelFactory(val repository: UserInfoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("未知ViewModel")

    }

}