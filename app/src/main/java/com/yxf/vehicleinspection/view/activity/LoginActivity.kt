package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityLoginBinding
import com.yxf.vehicleinspection.repository.JsCsCodeRepository
import com.yxf.vehicleinspection.room.JsCsCodeDao
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.IpHelper
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModel
import com.yxf.vehicleinspection.viewModel.JsCsCodeViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    val viewModel : JsCsCodeViewModel by viewModels { JsCsCodeViewModelFactory(MyApp.jsCsCodeRepository) }

    override fun init() {
        IpHelper.getIpAddress(this)

        binding.btnLogin.setOnClickListener {
//            val intent = Intent(this,HomeActivity::class.java)
//            startActivity(intent)
//            finish()
            val service = RetrofitService.create(QueryService::class.java)
            val call = service.Query("LYYDJKW001",IpHelper.getIpAddress(this),"{ \"userName\":\"admin\", “PassWord”:”123”, “IP”:”${IpHelper.getIpAddress(this)}” }")
            call.enqueue(object : Callback<List<Any>>{
                override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                    Toast.makeText(this@LoginActivity,"登录成功",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<List<Any>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}