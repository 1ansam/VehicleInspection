package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.repository.UserInfoRepository
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.viewModel.LoginViewModel
import com.yxf.vehicleinspection.viewModel.LoginViewModelFactory
import kotlin.math.log

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    private val loginViewModel by viewModels<LoginViewModel> { LoginViewModelFactory((application as MyApp).userInfoRepository) }
    override fun init() {
        if (SharedP.instance.getString("username","")!=null && SharedP.instance.getString("username","")!= ""){
            binding.cbRememberUsername.isChecked = true
        }
        binding.tvUsername.setText(SharedP.instance.getString("username", ""))
        binding.btnLogin.isEnabled = false
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = binding.tvUsername.text.toString()
                    .isNotEmpty() && binding.tvPassword.text.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        binding.tvUsername.addTextChangedListener(watcher)
        binding.tvPassword.addTextChangedListener(watcher)
        binding.btnLogin.setOnClickListener {
            if (binding.cbRememberUsername.isChecked) {
                SharedP.instance.edit().apply {
                    putString("username", binding.tvUsername.text.toString())
                    apply()
                }
            }else{
                SharedP.instance.edit().apply{
                    putString("username","")
                    apply()
                }
            }

            binding.pbLogin.visibility = View.VISIBLE
//            loginViewModel.isLoading(
//                binding.tvUsername.text.toString(),
//                binding.tvPassword.text.toString()).observe(this){
//                if (it) {
////                    binding.pbLogin.visibility = View.GONE
//                    val intent = Intent(this@LoginActivity, DisplayActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putSerializable("bean001",)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    binding.pbLogin.visibility = View.GONE
//                }
//            }
            loginViewModel.getUser(
                binding.tvUsername.text.toString(),
                binding.tvPassword.text.toString()
            ).observe(this){
                val intent = Intent(this,DisplayActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("bean001",it)
                intent.putExtra("user",bundle)
                startActivity(intent)
                finish()
            }
            loginViewModel.isLogin.observe(this){
                if (it) {
                } else {
                    binding.pbLogin.visibility = View.GONE
                }
            }


        }

    }


}