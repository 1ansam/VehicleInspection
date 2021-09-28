package com.yxf.vehicleinspection.view.Activity

import android.content.Intent
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseBindingActivity<ActivityWelcomeBinding>() {

    override fun init() {

        binding.btnStartEnjoy.setOnClickListener{
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}