package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    override fun init() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}