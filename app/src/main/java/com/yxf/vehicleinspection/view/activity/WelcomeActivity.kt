package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.permissionx.guolindev.PermissionX
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityWelcomeBinding
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory

class WelcomeActivity : BaseBindingActivity<ActivityWelcomeBinding>() {

    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((application as MyApp).dataDictionaryRepository)
    }
    private val systemParamsViewModel : SystemParamsViewModel by viewModels {
        SystemParamsViewModelFactory((application as MyApp).systemParamsRepository)
    }
    override fun init() {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "已获取所需权限", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "未获取到: $deniedList",
                        Toast.LENGTH_LONG).show()
                }
            }

                dataDictionaryViewModel.getDataDictionary().observe(this) {
                    dataDictionaryViewModel.insertDataDictionary(it)
                }

                systemParamsViewModel.getSystemParamsData().observe(this){
                    systemParamsViewModel.insertSystemParams(it)
                }

        binding.btnStartEnjoy.setOnClickListener {

            intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
            finish()


        }
        binding.btnParamsSync.setOnClickListener {
            dataDictionaryViewModel.getDataDictionary().observe(this){ list ->
                dataDictionaryViewModel.getDataDictionaryExist().observe(this){
                    if (it!=null){
                        dataDictionaryViewModel.deleteDataDictionary()
                    }else{
                        dataDictionaryViewModel.insertDataDictionary(list)
                    }

                }
            }
            systemParamsViewModel.getSystemParamsData().observe(this){ list ->
                systemParamsViewModel.getSystemParamsDataExist().observe(this){
                    if (it!=null){
                        systemParamsViewModel.deleteSystemParams()
                    }else{
                        systemParamsViewModel.insertSystemParams(list)
                    }
                }
            }

        }


    }

}




