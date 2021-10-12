package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.request.UserInfoRequest
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.repository.UserInfoRepository
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.ApiStatic
import com.yxf.vehicleinspection.singleton.GsonSingleton
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.IpHelper
import com.yxf.vehicleinspection.utils.JsonDataHelper
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModel
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModelFactory
import com.yxf.vehicleinspection.viewModel.LoginViewModel
import com.yxf.vehicleinspection.viewModel.LoginViewModelFactory
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"


    override fun init() {
        val viewModel = ViewModelProvider(this,
            LoginViewModelFactory(UserInfoRepository())).get(LoginViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            if (binding.tvUsername.text.toString() == "" || binding.tvPassword.text.toString() == "") {
                Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.isLogin(
                    binding.tvUsername.text.toString(),
                    binding.tvPassword.text.toString()).observe(this, Observer {
                    if (it) {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })

            }

        }
    }

}