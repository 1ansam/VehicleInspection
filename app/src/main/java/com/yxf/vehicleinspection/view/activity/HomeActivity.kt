package com.yxf.vehicleinspection.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityHomeBinding

class HomeActivity : BaseBindingActivity<ActivityHomeBinding>() {
    @SuppressLint("ResourceAsColor")
    override fun init() {
        binding.homeTitle.Alltitle.text = "功能模块选择"
        binding.btnPersonInspectionItem.setOnClickListener {
            val intent = Intent(this, PersonInspectionActivity::class.java)
            startActivity(intent)
        }
        binding.btnVerifySignature.setOnClickListener {
            val intent = Intent(this,SignatureActivity::class.java)
            startActivity(intent)
        }
    }

}