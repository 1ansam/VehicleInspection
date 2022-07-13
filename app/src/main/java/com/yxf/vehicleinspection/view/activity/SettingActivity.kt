package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.databinding.ActivitySettingBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.utils.setVisibility
import com.yxf.vehicleinspection.viewModel.*


/**
 * 网络设置Activity
 */
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
        binding.titleSetting.Alltitle.text = "参数设置"
        binding.tvIpAddress.setText(SharedP.instance.getString("ipAddress", "192.168.1.1"))
        binding.tvPort.setText(SharedP.instance.getString("ipPort", "80"))
        //设置保存并同步数据库
        binding.button.setOnClickListener {
            SharedP.instance.edit{
                putString("ipAddress", binding.tvIpAddress.text.toString())
                putString("ipPort", binding.tvPort.text.toString())
            }


            BaseUrlHelper.instance.setHostField(binding.tvIpAddress.text.toString())
            BaseUrlHelper.instance.setPortField(binding.tvPort.text.toString().toInt())
            dataDictionaryViewModel.deleteDataDictionary()
            systemParamsViewModel.deleteSystemParams()
            administrativeViewModel.deleteAdministrative()
            chargeItemViewModel.deleteChargeItem()
            setVisibility(this,binding.pbSync,true)
            dataDictionaryViewModel.getDataDictionary().second.observe(this){
                if (it.isNotEmpty()){
                    setVisibility(this,binding.pbSync,false)
                    Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
                    return@observe
                }
            }
            dataDictionaryViewModel.getDataDictionary().first.observe(this) dataDictionary@{ dataDictionaryList ->

                dataDictionaryViewModel.insertDataDictionary(dataDictionaryList)
                systemParamsViewModel.getSystemParamsData().second.observe(this){
                    if (it.isNotEmpty()){
                        setVisibility(this,binding.pbSync,false)
                        Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
                        return@observe
                    }
                }
                systemParamsViewModel.getSystemParamsData().first.observe(this) systemParamsData@{ systemParamsList ->

                    systemParamsViewModel.insertSystemParams(systemParamsList)
                    administrativeViewModel.getAdministrativeList().second.observe(this){
                        if (it.isNotEmpty()){
                            setVisibility(this,binding.pbSync,false)
                            Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
                            return@observe
                        }
                    }
                    administrativeViewModel.getAdministrativeList().first
                        .observe(this) adminstrative@{ administrativeList ->

                            administrativeViewModel.insertAdministrativeList(administrativeList)
                            chargeItemViewModel.getChargeItem().second.observe(this){
                                if (it.isNotEmpty()){
                                    setVisibility(this,binding.pbSync,false)
                                    Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
                                    return@observe
                                }
                            }
                            chargeItemViewModel.getChargeItem().first.observe(this) chargeItem@{ chargeItemList ->
                                chargeItemViewModel.insertChargeItem(chargeItemList)
                                dataDictionaryViewModel.insertEnd.observe(this) {
                                    if (it) {
                                        systemParamsViewModel.insertEnd.observe(this) {
                                            if (it) {
                                                administrativeViewModel.insertEnd.observe(this) {
                                                    if (it) {
                                                        setVisibility(this,binding.pbSync,false)
                                                        Toast.makeText(this,"参数同步成功", Toast.LENGTH_SHORT).show()
                                                        intent = Intent(this, LoginActivity::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    }else{
                                                        setVisibility(this,binding.pbSync,false)
                                                        Toast.makeText(
                                                            this,
                                                            "administrative同步失败",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            }else{
                                                setVisibility(this,binding.pbSync,false)
                                                Toast.makeText(
                                                    this,
                                                    "systemParams同步失败",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        }
                                    }else{
                                        setVisibility(this,binding.pbSync,false)
                                        Toast.makeText(
                                            this,
                                            "dataDictionary同步失败",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                            }

                        }
                }


            }


        }

    }



}