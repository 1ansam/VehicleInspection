package com.yxf.vehicleinspection.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.JcData_RG
import com.yxf.vehicleinspection.bean.JcDate_M1
import com.yxf.vehicleinspection.bean.T_IPCInfo
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    override fun init() {
        binding.login.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
}