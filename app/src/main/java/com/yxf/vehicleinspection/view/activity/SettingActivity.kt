package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.databinding.ActivitySettingBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.utils.getWifiRssi
import com.yxf.vehicleinspection.utils.setForceUnable
import com.yxf.vehicleinspection.utils.setVisibility
import com.yxf.vehicleinspection.viewModel.*

class SettingActivity : BaseBindingActivity<ActivitySettingBinding>() {
    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((application as MyApp).dataDictionaryRepository)
    }
    private val systemParamsViewModel: SystemParamsViewModel by viewModels {
        SystemParamsViewModelFactory((application as MyApp).systemParamsRepository)
    }
    private val administrativeViewModel: AdministrativeViewModel by viewModels {
        AdministrativeViewModelFactory((application as MyApp).administrativeRepository)
    }
    private val chargeItemViewModel: ChargeItemViewModel by viewModels {
        ChargeItemViewModelFactory((application as MyApp).chargeItemRepository)
    }

    override fun init() {
        binding.tvIpAddress.setText(SharedP.instance.getString("ipAddress", "192.168.1.1"))
        binding.tvPort.setText(SharedP.instance.getString("ipPort", "80"))

        binding.button.setOnClickListener {
            SharedP.instance.edit().apply {
                putString("ipAddress", binding.tvIpAddress.text.toString())
                putString("ipPort", binding.tvPort.text.toString())
                apply()
            }

            BaseUrlHelper.instance.setHostField(binding.tvIpAddress.text.toString())
            BaseUrlHelper.instance.setPortField(binding.tvPort.text.toString().toInt())
            dataDictionaryViewModel.deleteDataDictionary()
            systemParamsViewModel.deleteSystemParams()
            administrativeViewModel.deleteAdministrative()
            chargeItemViewModel.deleteChargeItem()
            dataDictionaryViewModel.getDataDictionary().observe(this) { dataDictionaryList ->
                setVisibility(this,binding.pbSync,true)
                dataDictionaryViewModel.insertDataDictionary(dataDictionaryList)
                systemParamsViewModel.getSystemParamsData().observe(this) { systemParamsList ->
                    systemParamsViewModel.insertSystemParams(systemParamsList)
                    administrativeViewModel.getAdministrativeList()
                        .observe(this) { administrativeList ->
                            administrativeViewModel.insertAdministrativeList(administrativeList)
                            chargeItemViewModel.getChargeItem().observe(this){ chargeItemList ->
                                chargeItemViewModel.insertChargeItem(chargeItemList)
                                dataDictionaryViewModel.insertEnd.observe(this) {

                                    if (it) {
                                        systemParamsViewModel.insertEnd.observe(this) {
                                            if (it) {
                                                administrativeViewModel.insertEnd.observe(this) {
                                                    if (it) {
                                                        setVisibility(this,binding.pbSync,false)
                                                        Toast.makeText(
                                                            this,
                                                            "参数同步成功",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        intent =
                                                            Intent(this, LoginActivity::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                }
                                            }

                                        }
                                    }

                                }
                            }

                        }
                }


            }


        }

    }



}