package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.content.Intent
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
        dataDictionaryViewModel.getDataDictionary().observe(this){ list ->
            dataDictionaryViewModel.getDataDictionaryExist().observe(this){
                if (it!=null){
                    dataDictionaryViewModel.updateDataDictionary(list)

                }else{
                    dataDictionaryViewModel.insertDataDictionary(list)
                }

            }
        }
        systemParamsViewModel.getSystemParamsData().observe(this){ list ->
            systemParamsViewModel.getSystemParamsDataExist().observe(this){
                if (it!=null){
                    systemParamsViewModel.updateSystemParams(list)
                }else{
                    systemParamsViewModel.insertSystemParams(list)
                }
            }
        }
        binding.btnStartEnjoy.setOnClickListener {
//            val testObject = LCObject("TestObject")
//            testObject.put("words","Hello World!")
//            testObject.saveInBackground().blockingSubscribe()
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)




            intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
            finish()
//            RxImagePicker
//                .create(DefaultImagePicker::class.java)
//                .openGallery(this)
//                .subscribe(Consumer {
//                    Glide.with(this)
//                        .load(it.uri)
//                        .into(binding.corporationLogo)
//                })

//            RxImagePicker
//                .create(DefaultImagePicker::class.java)
//                .openCamera(this)
//                .subscribe(Consumer {
//                    Glide.with(this)
//                        .load(it.uri)
//                        .into(binding.corporationLogo)
//                })

        }


    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
//            Log.e("TAG", "onActivityResult: REQUEST_VIDEO_CAPTURE", )
//        }
//    }

}




