package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.repository.JsCsCodeRepository
import com.yxf.vehicleinspection.room.JsCsCodeDao
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModel
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    val viewModel : JsCsCodeViewModel by viewModels { JsCsCodeViewModelFactory(MyApp.jsCsCodeRepository) }

    override fun init() {


        binding.btnLogin.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}