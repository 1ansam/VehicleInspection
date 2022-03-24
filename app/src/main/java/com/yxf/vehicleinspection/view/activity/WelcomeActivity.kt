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
import com.yxf.vehicleinspection.utils.getWifiRssi
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

        Log.e("TAG", "init: ${getWifiRssi()}", )
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "已获取所需权限", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "未获取到: $deniedList",
                        Toast.LENGTH_LONG).show()
                }
            }
//        dataDictionaryViewModel.deleteDataDictionary()
//        systemParamsViewModel.deleteSystemParams()
//        dataDictionaryViewModel.getDataDictionary().observe(this) { listDataDictionary ->
//            dataDictionaryViewModel.insertDataDictionary(listDataDictionary)
//            systemParamsViewModel.getSystemParamsData().observe(this){ listSystemParams ->
//                systemParamsViewModel.insertSystemParams(listSystemParams)
//                dataDictionaryViewModel.insertEnd.observe(this){
//                    if (it){
//                        systemParamsViewModel.insertEnd.observe(this){
//                            if (it){
//                                binding.pbSync.visibility = View.GONE
//                                Toast.makeText(this, "参数同步成功", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                    }
//                }
//            }
//
//
//        }
        binding.btnStartEnjoy.setOnClickListener {

            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()




        }
        binding.btnSetting.setOnClickListener {
            intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
            finish()



        }


    }

}




