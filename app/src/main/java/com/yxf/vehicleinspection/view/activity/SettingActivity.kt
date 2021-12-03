package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.databinding.ActivitySettingBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory

class SettingActivity : BaseBindingActivity<ActivitySettingBinding>() {
    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((application as MyApp).dataDictionaryRepository)
    }
    private val systemParamsViewModel : SystemParamsViewModel by viewModels {
        SystemParamsViewModelFactory((application as MyApp).systemParamsRepository)
    }
    override fun init() {
        binding.tvIpAddress.setText(SharedP.instance.getString("ipAddress","192.168.1.1"))
        binding.tvPort.setText(SharedP.instance.getString("ipPort","80"))

        binding.button.setOnClickListener {
            SharedP.instance.edit().apply {
                putString("ipAddress",binding.tvIpAddress.text.toString())
                putString("ipPort",binding.tvPort.text.toString())
                apply()
            }
            binding.pbSync.visibility = View.VISIBLE
            BaseUrlHelper.instance.setHostField(binding.tvIpAddress.text.toString())
            BaseUrlHelper.instance.setPortField(binding.tvPort.text.toString().toInt())
            dataDictionaryViewModel.deleteDataDictionary()
            systemParamsViewModel.deleteSystemParams()
            dataDictionaryViewModel.getDataDictionary().observe(this) { listDataDictionary ->
            dataDictionaryViewModel.insertDataDictionary(listDataDictionary)
            systemParamsViewModel.getSystemParamsData().observe(this){ listSystemParams ->
                systemParamsViewModel.insertSystemParams(listSystemParams)
                dataDictionaryViewModel.insertEnd.observe(this){
                    if (it){
                        systemParamsViewModel.insertEnd.observe(this){
                            if (it){
                                binding.pbSync.visibility = View.GONE
                                Toast.makeText(this, "参数同步成功", Toast.LENGTH_SHORT).show()
                                intent  = Intent(this,WelcomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }

                    }
                }
            }


        }

//            binding.pbSync.visibility = View.VISIBLE
//            dataDictionaryViewModel.getDataDictionaryExist(1).observe(this){
//                if (it!=null){
//                    dataDictionaryViewModel.deleteDataDictionary()
//                    systemParamsViewModel.getSystemParamsDataExist().observe(this){
//                        if (it!=null){
//                            systemParamsViewModel.deleteSystemParams()
//                        }else{
//                            dataDictionaryViewModel.getDataDictionary().observe(this) { listDataDictionary ->
//                                Log.e("TAG", "init: insertDataDictionaryStart", )
//                                dataDictionaryViewModel.insertDataDictionary(listDataDictionary)
//                                systemParamsViewModel.getSystemParamsData().observe(this){ listSystemParams ->
//                                    systemParamsViewModel.insertSystemParams(listSystemParams)
//                                    dataDictionaryViewModel.insertEnd.observe(this){
//                                        if (it){
//                                            systemParamsViewModel.insertEnd.observe(this){
//                                                if (it){
//                                                    binding.pbSync.visibility = View.GONE
//                                                    Toast.makeText(this, "参数同步成功", Toast.LENGTH_SHORT).show()
//                                                }
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }else{
//                    systemParamsViewModel.getSystemParamsDataExist().observe(this){
//                        if (it!=null){
//                            systemParamsViewModel.deleteSystemParams()
//                        }else{
//                            dataDictionaryViewModel.getDataDictionary().observe(this) { listDataDictionary ->
//                                Log.e("TAG", "init: insertDataDictionaryStart", )
//                                dataDictionaryViewModel.insertDataDictionary(listDataDictionary)
//                                systemParamsViewModel.getSystemParamsData().observe(this){ listSystemParams ->
//                                    systemParamsViewModel.insertSystemParams(listSystemParams)
//                                    dataDictionaryViewModel.insertEnd.observe(this){
//                                        if (it){
//                                            systemParamsViewModel.insertEnd.observe(this){
//                                                if (it){
//                                                    binding.pbSync.visibility = View.GONE
//                                                    Toast.makeText(this, "参数同步成功", Toast.LENGTH_SHORT).show()
//                                                }
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            dataDictionaryViewModel.deleteDataDictionary()
//            systemParamsViewModel.getSystemParamsDataExist().observe(this){
//                if (it!=null){
//                    systemParamsViewModel.deleteSystemParams()
//                }else{
//
//                }
//            }
//

        }

    }

}