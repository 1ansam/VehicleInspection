package com.yxf.vehicleinspection.view.Activity

import android.annotation.SuppressLint
import android.content.Intent
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityHomeBinding

class HomeActivity : BaseBindingActivity<ActivityHomeBinding>() {
    @SuppressLint("ResourceAsColor")
    override fun init() {
        binding.btnPersonInspectionItem.setOnClickListener {
            val intent = Intent(this, PersonInspectionActivity::class.java)
            startActivity(intent)
        }
    }

}