package com.yxf.vehicleinspection.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseBindingActivity<ActivityWelcomeBinding>() {

    override fun init() {

        binding.startEnjoy.setOnClickListener{
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}