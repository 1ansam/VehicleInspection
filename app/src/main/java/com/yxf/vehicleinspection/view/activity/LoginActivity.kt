package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.repository.UserInfoRepository
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.viewModel.LoginViewModel
import com.yxf.vehicleinspection.viewModel.LoginViewModelFactory

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"


    override fun init() {
        binding.tvUsername.setText(SharedP.getInstance().getString("username",null))
        val viewModel = ViewModelProvider(this,
            LoginViewModelFactory(UserInfoRepository())).get(LoginViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            if (binding.tvUsername.text.toString() == "" || binding.tvPassword.text.toString() == "") {
                Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.isLogin(
                    binding.tvUsername.text.toString(),
                    binding.tvPassword.text.toString()).observe(this, {
                    if (it) {
                        if (binding.cbRememberUsername.isChecked){
                            SharedP.getInstance().edit().apply{
                                putString("username",binding.tvUsername.text.toString())
                                apply()
                            }
                        }
                        val intent = Intent(this@LoginActivity, DisplayActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
            }

        }

    }


}