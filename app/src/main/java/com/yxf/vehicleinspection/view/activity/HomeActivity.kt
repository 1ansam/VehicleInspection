package com.yxf.vehicleinspection.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityHomeBinding
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModel
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModelFactory

class HomeActivity : BaseBindingActivity<ActivityHomeBinding>() {
    @SuppressLint("ResourceAsColor")
    override fun init() {
        binding.homeTitle.Alltitle.text = "功能模块选择"
        binding.btnPersonInspectionItem.setOnClickListener {
            val intent = Intent(this, PersonInspectionActivity::class.java)
            startActivity(intent)
        }
        binding.btnVerifySignature.setOnClickListener {
            val intent = Intent(this,VerifySignatureActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            val viewModel : JsCsCodeViewModel by viewModels { JsCsCodeViewModelFactory(MyApp.jsCsCodeRepository) }
            viewModel.downloadJsCsCode()
        }
    }

}