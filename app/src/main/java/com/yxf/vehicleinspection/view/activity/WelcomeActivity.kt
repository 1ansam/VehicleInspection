package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
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
        dataDictionaryViewModel.deleteDataDictionary()
        systemParamsViewModel.deleteSystemParams()
        dataDictionaryViewModel.getDataDictionary().observe(this) { list ->
            dataDictionaryViewModel.insertDataDictionary(list)
        }
        systemParamsViewModel.getSystemParamsData().observe(this){ list ->
            systemParamsViewModel.insertSystemParams(list)
        }
        binding.btnStartEnjoy.setOnClickListener {

            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

//                takeVideoIntent.resolveActivity(packageManager)?.also {
//                    Log.e("TAG", "init: $it", )
//                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
//                }



//            val filePath = "${Environment.getExternalStorageDirectory().absoluteFile}/DCIM/Screenshots/Record_2021-11-09-16-08-21.mp4"
//            val file = File(filePath)
//            val mediaType : MediaType? = MediaType.parse("multipart/form-data")
//            val requestBody = RequestBody.create(mediaType,file)
//            val call1 = RetrofitService.create(UploadFile::class.java).upload(
//                uploadFile("file",file,requestBody)
//            )
//            val call2 = RetrofitService.create(UploadFile::class.java).upload2(
//                uploadFile2("objFile",file,requestBody)
//            )
//            val call3 = RetrofitService.create(UploadFile::class.java).upload2(
//                uploadFile2("file",file,requestBody)
//            )
//            call2.enqueue(object : Callback<ResponseBody>{
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
            dataDictionaryViewModel.deleteDataDictionary()
            systemParamsViewModel.deleteSystemParams()
            dataDictionaryViewModel.getDataDictionary().observe(this) { list ->
                Log.e("TAG", "init: insertDataDictionaryStart", )
                dataDictionaryViewModel.insertDataDictionary(list)
            }
            systemParamsViewModel.getSystemParamsData().observe(this){ list ->
                Log.e("TAG", "init: insertSystemParamsStart", )
                systemParamsViewModel.insertSystemParams(list)
                Log.e("TAG", "init: insertSystemParamsEnd", )
            }

        }


    }

}




