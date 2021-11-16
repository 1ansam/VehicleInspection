package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.permissionx.guolindev.PermissionX
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityWelcomeBinding
import com.yxf.vehicleinspection.service.UploadFile
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
//
//                dataDictionaryViewModel.getDataDictionary().observe(this) {
//                    dataDictionaryViewModel.deleteDataDictionary()
//                    dataDictionaryViewModel.insertDataDictionary(it)
//                }
//
//                systemParamsViewModel.getSystemParamsData().observe(this){
//                    systemParamsViewModel.deleteSystemParams()
//                    systemParamsViewModel.insertSystemParams(it)
//                }
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

            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
//            val filePath = "${Environment.getExternalStorageDirectory().absoluteFile}/DCIM/Screenshots/Record_2021-11-09-16-08-21.mp4"
//            val file = File(filePath)
//            val mediaType : MediaType? = MediaType.parse("multipart/form-data")
//            val requestBody = RequestBody.create(mediaType,file)
//            val multipartBody = MultipartBody.Builder()
//                .addPart(requestBody)
//                .build()
//            Log.e("TAG", "init: ${multipartBody.part(0)}", )
//            val call = RetrofitService.create(UploadFile::class.java).upload(
//                multipartBody.part(0)
//            )
//            call.enqueue(object : Callback<ResponseBody>{
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//                    Log.e("TAG", "onResponse: ${response.code()}", )
//                    Log.e("TAG", "onResponse: $response", )
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    Log.e("TAG", "onFailure: ${t.message}", )
//                }
//            })


        }
        binding.btnParamsSync.setOnClickListener {
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

        }


    }

}




