package com.yxf.vehicleinspection.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.graphics.Color
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityHomeBinding

class HomeActivity : BaseBindingActivity<ActivityHomeBinding>() {
    @SuppressLint("ResourceAsColor")
    override fun init() {
        binding.personInspectionItem.setOnClickListener {
            val intent = Intent(this,PersonInsectionActivity::class.java)
            startActivity(intent)
        }
    }

}